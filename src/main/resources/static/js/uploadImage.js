function fileChange() {
    const file = document.getElementById('input_img');
    const form = new FormData();
    form.append("image", file.files[0]);

    const settings = {
        "url": "https://api.imgbb.com/1/upload?key=cbe21926adeaa051d2d2546b5ed23225",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    };

    $.ajax(settings).done(function (response) {
        const jx = JSON.parse(response);
        const image = document.getElementById('image');
        image.setAttribute('value', jx.data.url);
        const output = document.getElementById('output');
        output.setAttribute('height', '100');
        output.setAttribute('width', 'auto');
        output.src = jx.data.url;
    });
}