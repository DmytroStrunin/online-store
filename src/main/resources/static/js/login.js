let sleep = ms => new Promise(res => setTimeout(res, ms));

async function fx() {
    await sleep(500);
    getLoginForm();
}

fx();

function getLoginForm() {
    const loginPage = document
        .documentURI
        .toString()
        .match('.*/login.*');

    const registrationPage = document
        .documentURI
        .toString()
        .match('.*/registration.*');

    const loginMenu = document.createElement('a');
    loginMenu.setAttribute('data-bs-toggle', 'modal');
    loginMenu.setAttribute('data-bs-target', '#exampleModal');

    const loginMod = document.getElementById('loginMod');

    if (loginPage || registrationPage) {
        if (registrationPage) {
            document.getElementById('tab-register')
                .click();
            console.log(registrationPage);
        }
        loginMod.click();
    }
}