const sortSelector = document.getElementById('sort');
const filterSelector = document.getElementById('filter');

for (const selectElement of sortSelector) {
    updateSelect(selectElement);
}

for (const selectElement of filterSelector) {
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