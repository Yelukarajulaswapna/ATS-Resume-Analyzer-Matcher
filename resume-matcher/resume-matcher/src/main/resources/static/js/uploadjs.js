document.getElementById("resumeForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const fd = new FormData();
    fd.append("resume", document.getElementById("resume").files[0]);
    fd.append("jobDescription", document.getElementById("jobDescription").value);

    fetch("/api/resume/upload", {
        method: "POST",
        body: fd
    })
    .then(res => res.json())
    .then(data => {

        console.log("Response:", data);
        alert(JSON.stringify(data));

        document.getElementById("result").style.display = "block";

        // ❌ Backend failed
        if (!data || data.estimated === true) {
            document.getElementById("score").innerText = "0";
            document.getElementById("scoreText").innerText = "Analysis Failed";
            return;
        }

        // ✅ Overall Score
        const score = Number(data.overallScore);

        if (isNaN(score)) {
            document.getElementById("score").innerText = "0";
            document.getElementById("scoreText").innerText = "Invalid Score";
            return;
        }

        document.getElementById("score").innerText = score;
        document.getElementById("scoreText").innerText = score;

        document.getElementById("ring")
            .style.setProperty("--percent", (score * 3.6) + "deg");

        // ✅ Update breakdown scores
        document.getElementById("impactScore").innerText =
            data.experienceScore + " / 100";

        document.getElementById("skillsScore").innerText =
            data.skillScore + " / 100";

        // ✅ Matched Skills
        const matchedDiv = document.getElementById("matchedSkills");
        matchedDiv.innerHTML = "";
        data.matchedSkills.forEach(skill => {
            matchedDiv.innerHTML +=
                `<span class="badge good-badge">${skill}</span>`;
        });

        // ✅ Missing Skills
        const missingDiv = document.getElementById("missingSkills");
        missingDiv.innerHTML = "";
        data.missingSkills.forEach(skill => {
            missingDiv.innerHTML +=
                `<span class="badge bad-badge">${skill}</span>`;
        });

    })

    .catch(err => {
        console.error(err);
    });
});