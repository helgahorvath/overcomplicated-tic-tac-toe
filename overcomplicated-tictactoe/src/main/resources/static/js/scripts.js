$(".square").click(function (e) {
    let squareId = $(this).attr("id");
    console.log($(this).children());
    if ($(this).children().length === 0) {
        $.ajax(
            {
            url: "/game-move",
            method: 'POST',
            dataType: "text",
            data: squareId,
            success: function (data) {
                let parsed = JSON.parse(data);
                let classToAdd = parsed.sign === "O"? "fa-circle-o": "fa-times";
                let iToAppend = document.createElement("i");
                iToAppend.classList.add("fa", classToAdd);
                document.getElementById(squareId).appendChild(iToAppend);
                if (parsed.won === "yes") {
                    $("#modal-text").text(`${parsed.sign} won.`);
                    $("#gameover").modal('show');
                }
            }})
    } else {
        console.log("taken")
    }
});