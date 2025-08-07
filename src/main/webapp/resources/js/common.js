document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (e) {
        if(e.target.closest("#btnLogout")) {
            buttonEvent.logout();
        } else if(e.target.closest("#btnPrev")) {
            buttonEvent.prev();
        }
    });
});

const buttonEvent = {
    logout() {
        const csrf = document.getElementById("csrf").value;
        const csrfInput = document.getElementById("csrf");
        const csrfParam = csrfInput.name;
        const csrfToken = csrfInput.value;

        const form = document.createElement("form");
        form.method = "POST";
        form.action = "/logout";

        const csrfField = document.createElement("input");
        csrfField.type = "hidden";
        csrfField.name = csrfParam;
        csrfField.value = csrfToken;

        form.appendChild(csrfField);

        document.body.appendChild(form);
        form.submit();
    },
    prev() {
        window.history.back();
    }
}