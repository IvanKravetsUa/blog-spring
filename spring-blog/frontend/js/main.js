$(document).ready(function () {

  let userId = 1;
  let postId = 1;

  showAllUsers();
  showAllPosts();

  showPostById(postId);
  showUserById(userId);

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

  if (userPassword == userPasswordConfirm) {
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
      complete: function (serverResponse) {
        console.log(serverResponse);
      }
    });
  } else {
    alert("Passwords don't match")
  }
}

function showUserById(userId) {
  $.ajax({
    url: "http://localhost:8080/users/" + userId,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let userById = serverResponse.responseJSON;
      $("#accountInformationBody").append(
        `
             <p><i class="far fa-user-circle"></i>Avatar</p>
              <img src="images/autorPost.jpg" alt="" class="img-fluid">
              <hr>
              <p>UserId</p>
              <h5>${userById.id}</h5>
              <hr>
              <div class="row">
        
              <div class="col">
                  <p>First Name</p>
                  <h5>${userById.lastName}</h5>
                </div>

                <div class="col">
                  <p>Last Name</p>
                  <h5>${userById.lastName}</h5>
                </div>
              </div>
              <hr>

              <p><i class="fas fa-envelope"></i>E-mail: <h5>${userById.email}</h5>
              </p>
              <hr>
              <p><i class="fas fa-transgender"></i>Sex: <h5>${userById.sex}</h5>
              </p>
              <hr>
              <p><i class="fab fa-angellist"></i>Reputation: </p>
              <h5>${userById.reputation}</h5>
                `
      );
      $("#accountInformationFooter").append(
        `<p><i class="fas fa-clock"></i>Date of creation:<h5></h5>${userById.accountCreatedDate}</p>`
      );
    }
  });
}

function showAllUsers() {
  $.ajax({
    url: "http://localhost:8080/users",
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      console.log(serverResponse.responseJSON);
      let users = serverResponse.responseJSON;

      $.each(users, function (key, value) {

      });
    }
  });
}

function showAllPosts() {
  $.ajax({
    url: "http://localhost:8080/posts",
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      console.log(serverResponse.responseJSON);
      let posts = serverResponse.responseJSON;
      $.each(posts, function (key, value) {
        $("#postView").append(
          `
          <div class="postsBody col-lg-6 col-md-12">
            <div class="view overlay rounded z-depth-1-half mb-3">
              <img src="images/img1.jpeg" alt="" class="img-fluid">
              <a href="postPage.html">
                <div class="mask rgba-white-slight"></div>
              </a>
            </div>
           
            <div class="news-data">
              <a href="#" class="light-blue-text">
                <h6>
                  <i class="fas fa-tags"></i>
                  <strong></strong>
                </h6>
              </a>
              
              <p>
                <strong>
                  <i class="fas fa-clock"></i>
                  ${value.createdDate}
                </strong>
              </p>
            </div>

            <div class="media-likes red-text col">
                <h5><i class="fas fa-heart"></i>Likes</h5>
                 <p><strong>${value.marks}</strong></p>
               </div>
          
            <h3>
              <a href="postPage.html">
                <strong>${value.title}</strong>
              </a>
             
            </h3>
            <p>${value.description}</p></div>
                    `
        );
      });
    }
  });
}

function showPostById(postId) {
  $.ajax({
    url: "http://localhost:8080/posts/" + postId,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let postById = serverResponse.responseJSON;

      let tagsInPost = postById.tags
      let userByPost = postById.user

      $("#postCardBody").append(
        `
        <div class="card-body">
            
            <p class="h5 my4">${postById.title}</p>
            
            <blockquote class="blockquote">
                <p class="mb-0">${postById.title}</p>
                <footer class="blockquote-footer">
                    Lorem, ipsum dolor. <cite title="Source title">Source title</cite>
                </footer>
            </blockquote>
           
            
            <p>${postById.description}</p>
        </div>
        `
      );
      $("#postCardFooterLike").append(
        `
          <p><strong>8</strong>
          <i class="fas fa-heart"></i><button type="button"
              class="btn btn-outline-danger waves-effec p-2"><i
                  class="far fa-thumbs-up" aria-hidden="true"></i></button>
          <button type="button" class="btn btn-outline-danger waves-effec p-2"><i
                  class="far fa-thumbs-down" aria-hidden="true"></i></button>
        </p>
        `
      );
      $.each(tagsInPost, function (key, value) {
        $("#postCardFooterTags").append(
          `
          <a href="#" class="light-blue-text">
             <h6>
              <p><strong>Tags:</strong></p>
               <i class="fas fa-tags"></i>
               <strong>${value.name}</strong>
              </h6>
            </a> 
          `
        );
      });
      $("#postCardFooterTime").append(
        `
        <p>
          <p>Time create:</p>
            <strong>
                <i class="fa fa-clock-o"></i>${postById.createdDate}</strong>
        </p>
        `
      );
      $("#postAuthorPost").append(
        `
        <div class="media d-block d-md-flex mt-3">
            <img style="width:100px" src="images/autorPost.jpg" alt=""
                class="d-flex mb-3 mx-auto z-depth-1">
            <div class="media-body text-center text-md-left ml-md-3 ml-0">
                <h5 class="mt-0 font-weight-bold">${userByPost.firstName} ${userByPost.lastName}</h5>
                <p>Reputation: ${userByPost.reputation}</p>
            </div>
        </div>
        `
      );
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


// Вкладенні попередні пости
{/* <hr>
            <div class="row">
              <div class="col-md-3">

                <div class="view overlay rounded z-depth-1">
                  <img src="images/img2.jpeg" alt="" class="img-fluid">
                </div>
              </div>
              <div class="col-md-9">
                <p class="dark-gray-text">
                  <strong>
                    <i class="fas fa-clock"></i>
                    19/08/2018
                  </strong>
                </p>
                <a href="postPage.html">
                  Lorem ipsum dolor sit amet consectetur.
                  <i class="fa fa-angle-right float-right"></i>
                </a>
              </div>
            </div>

            <hr>

            <div class="row">
              <div class="col-md-3">

                <div class="view overlay rounded z-depth-1">
                  <img src="images/img2.jpeg" alt="" class="img-fluid">
                </div>
              </div>
              <div class="col-md-9">
                <p class="dark-gray-text">
                  <strong>
                    <i class="fas fa-clock"></i>
                    19/08/2018
                  </strong>
                </p>
                <a>
                  Lorem ipsum dolor sit amet consectetur.
                  <i class="fa fa-angle-right float-right"></i>
                </a>
               </div>
              </div> */}
