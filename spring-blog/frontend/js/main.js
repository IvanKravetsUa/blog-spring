$(document).ready(function () {

  let userId = 1;
  let postId = 5;

  showAllUsers();
  showAllPosts();

  showPostById(postId);
  showUserById(userId);

  // deletePost(postId);

  $("#sendRegistration").submit(signup);

  $("#submitLogin").submit(login);

  $(document).on("click", "#postView button", function (e) {
    let btnId = e.target.id;
    let postId = btnId.split("-")[1];
    deletePost(postId);
  });

  $(document).on("change", "#accountInformationBody input", function (e) {
    // console.log(e.target.id)
    let inputId = e.target.id;
    let userId = inputId.split("-")[1];
    // console.log(userId)

    uploadUserAvatar(userId);
  })

  let pageNumber = 0;
  $("#btnPrev").on("click", function () {
    if (pageNumber > 0) {
      pageNumber--;
    }
    showAllPosts(pageNumber);
  });

  $("#btnNext").on("click", function () {
    pageNumber++;
    showAllPosts(pageNumber);
  });

  $(document).on("click", "#postCardFooterLike button", function (e) {
    console.log(e.target.id);
    let btnId = e.target.id;
    let postId = btnId.split("-")
    console.log("postId" + postId);
  });

  $(document).on("click", "#postView", function (e) {
    console.log(e.target.id);
  });

});

function uploadUserAvatar(userId) {
  let formData = new FormData();
  formData.append("imageFile", $("#image-" + userId)[0].files[0]);

  $.ajax({ // localhost:8080/users/10/image
    url: "http://localhost:8080/users/" + userId + "/image",
    method: "POST",
    contentType: false,
    processData: false,
    data: formData,
    complete: function (res) {
      console.log(res);

      if (res.status == 202) {
        $("#accountInformationBody").empty();
        showUserById(userId)
      }
    }
  })
}

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
        if (serverResponse.status == 201) {
          alert("User added to database");
        }
      }
    });
  } else {
    alert("Passwords don't match")
  }
}

function showUserById(userId) {
  let IMAGE_URL = "http://localhost:8080/users/image?imageName=";

  $.ajax({
    url: "http://localhost:8080/users/" + userId,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let userById = serverResponse.responseJSON;
      $("#accountInformationBody").append(
        `
             <p><i class="far fa-user-circle"></i>Avatar</p>
             <img src="${userById.image != null? (IMAGE_URL + userById.image) : ""}" class="img-fluid"">
              <div class="input-group m-2">
               <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroupFileAddon01">Upload new avatar</span>
               </div>
               <div class="custom-file">
                 <input type="file" class="custom-file-input" id="image-${userById.id}" aria-describedby="inputGroupFileAddon01">
                 <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
               </div>
             </div>
             
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

function deletePost(postId) {
  let deletePost = confirm("Ви впевнені що хочете видалити пост")
  if (deletePost) {
    deletePostRequest(postId);
  }
}

function deletePostRequest(postId) {
  $.ajax({
    url: "http://localhost:8080/posts/" + postId,
    method: "DELETE",
    contentType: "application/json",
    complete: function (serverResponse) {
      if (serverResponse.status == 200) {
        alert("post deleted");
      }
    }
  });
}

function showAllPosts(pageNumber) {
  $.ajax({
    url: "http://localhost:8080/" + "posts/page?page=" + pageNumber,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      console.log(serverResponse.responseJSON);
      let posts = serverResponse.responseJSON;
      let tagsInPost = posts.tags

      $("#postView").empty();

      if (posts.totalPages <= pageNumber) {
        $("#btnNext").prop("disabled", true);
      }

      $.each(posts.content, function (key, post) {
        let marksTotal = 0;
        post.marks.forEach(function () {
          marksTotal++
        });

        let tagsNames = [];
        $.each(post.tags, function (key, tag) {
          tagsNames.push(tag.name)
        });

        $("#postView").append(
          `
          <div id = "postViewId-${post.id}" class="postsBody col-lg-6 col-md-12"></p>
            <div class="view overlay rounded z-depth-1-half mb-3">
              <img src="images/img1.jpeg" alt="" class="img-fluid">
              <a href="#">
                <div class="mask rgba-white-slight"></div>
              </a>
            </div>
           
            <div class = "row" class="news-data">
              <a href="#" class="col-6 light-blue-text">
                <h6 id = "postId"><i class="fas fa-tags"></i>Tags: ${tagsNames}</h6>
              </a>     
                
            </div>
            <div class = "likeAndTimePost row">
             <div class="media-likes red-text col">
                <p><strong><i class="fas fa-heart"></i>Likes ${marksTotal}</strong></p>
               </div>
            <p class = "col">
                  <i class="fas fa-clock"></i> createdDate:
                  ${post.createdDate}
            </p>
          </div>
            <h4>
              <a href="postPage.html">
                <strong>${post.title}</strong>
              </a>
             
            </h4>
            <p>${post.description}</p><button class="btn btn-danger btn-sm" id="post-${post.id}">Delete</button>
           
          </div>
            
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
      let commentsByPost = postById.comments
      let marksByPost = postById.marks

      let commentsSize = 0;
      commentsByPost.forEach(function () {
        commentsSize++
      });

      let postMarksSize = 0;
      marksByPost.forEach(function () {
        postMarksSize++
      });


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
        `<h5><i class="fas fa-heart"></i><strong>Likes ${postMarksSize}</strong></h5>
          <button id="postLike-${postById.id}" type="button" class="btn btn-outline-danger waves-effec p-2"><i
                  class="far fa-thumbs-up" aria-hidden="true"></i></button>
          <button id="postDislike-${postById.id}" type="button" class="btn btn-outline-danger waves-effec p-2"><i
                  class="far fa-thumbs-down" aria-hidden="true"></i></button>
        </p>
        `
      );
      $.each(tagsInPost, function (key, tags) {
        $("#postCardFooterTags").append(
          `
          <a href="#" class="light-blue-text">
             <h6>
              <p><strong>Tags:</strong></p>
               <i class="fas fa-tags"></i>
               <strong>${tags.name}</strong>
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


      $("#postCommentsSize").append(
        `
        ${commentsSize} comments
        `
      );

      $.each(commentsByPost, function (key, comments) {
        let marksValue = 0;
        comments.marks.forEach(function () {
          marksValue++
        });
        $("#postCommentsPost").append(
          `
        <div class="card-body">

        <div class="media d-block d-md-flex mt-4">
            <img src="images/portred.jpg" alt="" class="d-flex mb-3 mx-auto">
            <div class="media-body text-center text-md-left ml-md-3 ml-0">
                <h5 class="mt-0 front-weight-bold">${comments.user.firstName} ${comments.user.lastName}
                    <a href="#" class="pull-right">
                        <i class="fa fa-reply"></i>
                    </a>
                </h5>
                ${comments.body}
            </div>
        </div>
       </div> 
        <div class="card-footer">
       <div class="media-likes red-text col">
          <h5><i class="fas fa-heart"></i><strong>Likes ${marksValue}</strong></h5>
         </div>
      </div> 
      
        `
        );
      });


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
