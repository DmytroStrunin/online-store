const sortSelector = document.getElementById('sort');

for (const selectElement of sortSelector) {
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