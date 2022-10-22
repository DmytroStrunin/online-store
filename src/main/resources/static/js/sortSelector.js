const select = document.getElementById('sort');
for (const selectElement of select) {
    updateSortSelect(selectElement);
}

function updateSortSelect(el) {
    const sort = document
        .documentURI
        .toString()
        .match(el.value);
    if (sort){
        el.setAttribute('selected', 'selected');
    }
}