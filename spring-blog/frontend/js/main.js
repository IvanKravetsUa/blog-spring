$(document).ready(function(){

    

    $("#sendRegistration").submit(signup);

    $("#submitLogin").submit(login);

});

function signup() {
    let userFirstName = $("#userFirstNameRegistration").val();
    let userLastName = $("#userLastNameRegistration").val();
    let userEmail = $("#userEmailRegistration").val();
    let userPassword = $("#userPassRegistration").val();
    let userPasswordConfirm = $("#userPassConfirmRegistration").val();
    let userSexType = $("#userSexRegistration").val();

    let user = {
        firstName: userFirstName,
        lastName: userLastName,
        email: userEmail,
        password: userPassword,
        passwordConfirm: userPasswordConfirm,
        sex: userSexType,
        reputation: 0
    };

    console.log(user);
    return false;
}

function login() {

    let userEmailLogin = $("#userEmailLogin").val();
    let userPasswordLogin = $("#userPassLogin").val();

    let userLogin = {
        email: userEmailLogin,
        password: userPasswordLogin
    };
    
    console.log(userLogin)
    return false;
}
