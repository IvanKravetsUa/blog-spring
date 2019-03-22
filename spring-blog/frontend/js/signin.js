$(document).ready(function() {

    $("#submitLogin").on("submit", function() {
        
        console.log("click");
        signin();
        return false;
    })

});

function signin() {
    let userLogin = {
        email: $("#userEmailLogin").val(),
        password: $("#userPassLogin").val()
    };

    $.ajax({
        url: "http://localhost:8080/auth/signin",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(userLogin),
        complete: function(res) {
            let result = res.responseJSON;

            if(res.status == 200) {
                if (result.token) {
                    localStorage.setItem("auth_token", result.token)
                    localStorage.setItem("user_email", $("#userEmailLogin").val())

                    window.location.href = "index.html";
                }
            }
        }
    });
    return false;
}