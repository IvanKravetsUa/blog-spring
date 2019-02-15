$(document).ready(function(){

    showAllUsers();
    showAllPosts();

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

    $.ajax({
        url: "http://localhost:8080/users",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(user),
        complete: function(serverResponse) {
            console.log(serverResponse);
        }
    });

}

function showAllUsers() {
    $.ajax({
        url: "http://localhost:8080/users",
        method: "GET",
        contentType:"application/json",
        complete: function(serverResponse) {
            console.log(serverResponse);      
        }
    });
}

function showAllPosts() {
    $.ajax({
        url: "http://localhost:8080/users",
        method: "GET",
        contentType:"application/json",
        complete: function(serverResponse) {
            console.log(serverResponse);      
        }
    });
}

function login() {

    let userEmailLogin = $("#userEmailLogin").val();
    let userPasswordLogin = $("#userPassLogin").val();

    let userLogin = {
        email: userEmailLogin,
        password: userPasswordLogin
    };
    
    // $.ajax({
    //     url: "http://localhost:8080/users",
    //     method: "POST",
    //     contentType: "application/json",
    //     data: JSON.stringify(userLogin),
    //     complete: function(serverResponse) {
    //         console.log(serverResponse);
    //     }
    // });
}



// .append -- внести данні записує строкою (все підряд) в блок чи параграф або наприклад добавляє новий елемент в кінець лісту
// .prepend -- те саме шо апенд тільки напочаток ліста
// .appendTo -- міняє місцями
// .before("<h4>Hello</h4>") -- добавляє елемент перед елементом в дужках .after те саме тільки після
// .empty -- зробити пустим елемент
// .detach() -- видалити(забрати) елемент
// .attr()
// .keyup() -- збирає дані вводу з клавіатури
// .wrap("<h1>") -- обгортає елемент в те що в душках
// .html -- записує(виводить на сторінку) і автоматично оновлює не пише строкою
// .heyup -- (в функції потрібно console.log(e.target.value)) читає в реальному часі з інпуту 
// .css({color:"red", background:"#ccc"}) -- приклад задання кількох параметрів
// .addClass -- додати клас якщо його нема до вже існуючого обєкта
// .removeClass -- видалити клас вже існуючого обєкта
// .toggleClass("newClass") -- переключення (зміна) класу
// .text -- записати текст в обєкт
// .html("<h3> Hello World</h3>") -- додати обект в заданий клас
// .each(Array, function(i, val){} -- вибирає кожен елемент Масива по черзі до кінця масива
// .toArray -- перетворює в масив  Приклад: var newArr = $('ul#list li').toArray();
//  
// Приклад є масив var myArr = ["name1", "name2"] 
//  $.each(myArr, function(i, val){
//      $("#users").append("<li>"+val+"</li>");  
// })   функція добавляє елементи масиву в ліст на сторінку
    

