const select = document.getElementById('sort');
const filter = document.getElementById('filter');
for (const selectElement of select) {
    updateSelect(selectElement);
}

for (const selectElement of filter) {
    updateSelect(selectElement);
}

function updateSelect(el) {
    const sort = document
        .documentURI
        .toString()
        .match(el.value);
    if (sort){
        el.setAttribute('selected', 'selected');
    }
}