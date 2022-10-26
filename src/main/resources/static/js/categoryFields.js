addField();

function addField() {
    const form = document.getElementById("in");
    const el = document.createElement('input');
    el.setAttribute('class', 'form-control mt-1');
    el.setAttribute('placeholder', 'Add new feature');
    el.setAttribute('name', 'features');
    el.addEventListener('input',
        (e) => {
            addField(e.target);
        }, {once: true});
    form.appendChild(el);
}