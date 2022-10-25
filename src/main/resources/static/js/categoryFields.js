addField();

function addField() {
    const form = document.getElementById("in");
    const el = document.createElement('input');
    const br = document.createElement('br');
    el.setAttribute('placeholder', 'Add new feature');
    el.setAttribute('name', 'features');
    el.addEventListener('input',
        (e) => {
            addField(e.target);
        }, {once: true});
    form.appendChild(el);
    form.appendChild(br);
}