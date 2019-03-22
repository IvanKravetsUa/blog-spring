$(document).ready(function () {
  let userId = localStorage.getItem("autorPostId");
  let postId = localStorage.getItem("postById");

  $(document).on("click", "#accountMain", function (e) {
    localStorage.setItem("autorPostId", localStorage.getItem("authorized_userId"))
  });

  $("#logoutBtn").hide();

  let authToken = localStorage.getItem("auth_token");
  if (authToken) {
    $.ajaxSetup({
      headers: {
        'Authorization': 'Bearer ' + authToken
      }
    });

    let role = JSON.parse(atob(authToken.split(".")[1]));
    console.log(role)

    $(document).on("click", "#postView button", function (e) {
      let btnId = e.target.id;
      let postId = btnId.split("-")[1];
      if (role.auth == "ROLE_ADMIN") {
        deletePost(postId);
      } else {
        alert("You are not")
      }
    });

    if (role.auth == "ROLE_USER" || role.auth == "ROLE_ADMIN") {

      $("#creatingPost").on("submit", function () {
        creatingPost(localStorage.getItem("authorized_userId"))
        return false;
      });

      $("#creatingCommentForm").on("submit", function() {
        createComment(localStorage.getItem("authorized_userId"), localStorage.getItem("postById"));
        return false;
      });

      $(document).on("click", "#commentCardFooterLike button", function (e) {
        let markStatus = 1
        let commentId = e.target.id.slice(9);
        console.log(commentId)
        creatingLikeByComment(localStorage.getItem("authorized_userId"), commentId, markStatus)
        return false;
      });

      $(document).on("click", "#commentCardFooterDislike button", function (e) {
        let markStatus = 0
        let commentId = e.target.id.slice(10);
        creatingLikeByComment(localStorage.getItem("authorized_userId"), commentId, markStatus)
        return false;
      });

      $(document).on("click", "#postCardFooterLike button", function (e) {
        let markStatus = 1
        creatingLikeByPost(localStorage.getItem("authorized_userId"), postId, markStatus)
        location.reload();
        $("#postLike-"+ postId).hide();
        $("#postDislike-"+ postId).hide();
        return false;
      });

      $(document).on("click", "#postCardFooterDislike button", function (e) {
        let markStatus = 0
        creatingLikeByPost(localStorage.getItem("authorized_userId"), postId, markStatus)
        location.reload();
        $("#postLike-"+ postId).hide();
        $("#postDislike-"+ postId).hide();
        return false;
      });
    }
    $("#logoutBtn").show();
  } else {
    // location.href= "block.html";
    $("#postCreatingPage").hide();
    $("#userInformation").hide();
  }

  $("#logoutBtn").on("click", function () {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("authorized_userId");
    window.location.href = "index.html";
  })

  showAllUsers();
  showAllPosts();
  showAllTags();
  showAllComments();

  showUserById(userId);
  showPostById(postId);
  showPostOfTheDay(localStorage.getItem("postOfTheDay"));
  showCommentOfTheDay(localStorage.getItem("commentOfTheDayId"));

  $("#sendRegistration").submit(signup);

  $(document).on("change", "#postImage input", function (e) {
    uploadPostImage(postId);
  })

  $(document).on("change", "#accountInformationBody input", function (e) {
    // console.log(e.target.id)
    let inputId = e.target.id;
    let userId = inputId.split("-")[1];
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

  $(document).on("click", "#postView strong", function (e) {
    let aViewId = e.target.id
    let postViewId = aViewId.slice(10);
    localStorage.setItem("postById", postViewId)
    window.location.href = "postPage.html";
    console.log(postViewId);
  });

  $(document).on("click", "#postAuthorPost strong", function (e) {
    let autorPostId = e.target.id.slice(9);
    showUserById(autorPostId);
    localStorage.setItem("autorPostId", autorPostId)
    window.location.href = "accountPage.html";
    console.log(autorPostId);
  });

  $(document).on("click", "#postCommentsPost strong", function (e) {
    let autorCommentId = e.target.id.slice(9);
    showUserById(autorCommentId);
    localStorage.setItem("autorPostId", autorCommentId)
    window.location.href = "accountPage.html";
    console.log(autorPostId);
  });

});

function creatingLikeByComment(userId, commentId, markStatus) {
  let authToken = localStorage.getItem("auth_token");
  let like = {
    markStatus: markStatus
  }

  $.ajax({
    url: "http://localhost:8080/marks/" + userId + "/comment/" + commentId,
    headers: {
      'Authorization':'Bearer ' + authToken
    },
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify(like),
    complete: function (serverResponse) {
      console.log(serverResponse);
      if (serverResponse.status == 200) {
        alert("Success");
        location.reload();
      }
    }
  });
}

function creatingLikeByPost(userId, postId, markStatus) {
  let authToken = localStorage.getItem("auth_token");

  let like = {
    markStatus: markStatus
  }

  $.ajax({
    url: "http://localhost:8080/marks/" + userId + "/post/" + postId,
    headers: {
      'Authorization':'Bearer ' + authToken
    },
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify(like),
    complete: function (serverResponse) {
      console.log(serverResponse);
      if (serverResponse.status == 200) {
        alert("Success");
      }
    }
  });
}

function creatingDislikeByPost(userId, postId) {
  let like = {
    markStatus: 0
  }
  $.ajax({
    url: "http://localhost:8080/marks/" + userId + "/post/" + postId,
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify(like),
    complete: function (serverResponse) {
      console.log(serverResponse);
    }
  });
}

function creatingPost(userId) {
  let tagsSelected = []
  $("#tagsSelect :selected").each(function () {
    tagsSelected.push({"name": $(this).val()});
  });
  tagsSelected.push({"name":  $("#postTagCreating").val()} )

  let post = {
    title: $("#postTitleCreating").val(),
    description: $("#postBodyCreating").val(),
    tags: tagsSelected
  };

  $.ajax({
    url: "http://localhost:8080/posts/user/" + userId,
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify(post),
    complete: function (serverResponse) {
      let post = serverResponse.responseJSON;
      console.log(post)
      if(serverResponse.status == 201) {
        localStorage.setItem("postById", post.id)
        window.location.href = "postPage.html";
      }
    }
  });
}

function uploadPostImage(postId) {
  let formData = new FormData();
  formData.append("imageFile", $("#image-" + postId)[0].files[0]);
  $("#imagesForUpload").append(`
        <img src="${formData}" class="img-fluid"">
      `)

  $.ajax({ // localhost:8080/users/10/image
    url: "http://localhost:8080/posts/" + postId + "/image",
    method: "POST",
    contentType: false,
    processData: false,
    data: formData,
    complete: function (res) {
      if (res.status == 202) {
        location.reload();
      }
    }
  })
}

function showAllTags() {
  $.ajax({
    url: "http://localhost:8080/tags",
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let tags = serverResponse.responseJSON;
      $.each(tags, function (key, tag) {
        $("#tagsSelect").append(
          `
          <option id="tag${tag.id}" value="${tag.name}">${tag.name}</option>
        `)
      });
    }
  });
}

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
      url: "http://localhost:8080/auth/signup",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(user),
      complete: function (serverResponse) {
        console.log(serverResponse);
        if (serverResponse.status == 201) {
          alert("Success");
          window.location.href = "index.html";
        }
      }
    });
  } else {
    alert("Passwords don't match")
  }
  return false;
}

function showUserById(userId) {
  let IMAGE_URL = "http://localhost:8080/users/image?imageName=";

  $.ajax({
    url: "http://localhost:8080/users/" + userId,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let userById = serverResponse.responseJSON;
    
      localStorage.setItem("userAccountInformationById", userById.id);
      $("#accountInformationBody").append(
        `
             <p><i class="far fa-user-circle"></i>Avatar</p>
             <img src="${userById.image != null ? (IMAGE_URL + userById.image) : ""}" class="img-fluid"">
              <div id = "userImage${userById.id}" class="input-group m-2">
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
                  <h5>${userById.firstName}</h5>
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

      if(localStorage.getItem("authorized_userId") != userById.id) {
        $("#userImage"+userById.id).hide();
      }
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

      $.each(users, function (key, user) {
        if (user.email == localStorage.getItem("user_email")) {
          localStorage.setItem("authorized_userId", user.id)
        }
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
  let IMAGE_URL = "http://localhost:8080/users/image?imageName=";
  $.ajax({
    url: "http://localhost:8080/" + "posts/page?page=" + pageNumber,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      console.log(serverResponse.responseJSON);
      let posts = serverResponse.responseJSON;
      localStorage.setItem("marksOfTheDay", -1)

      $("#postView").empty();

      if (posts.totalPages <= pageNumber) {
        $("#btnNext").prop("disabled", true);
      }

      $.each(posts.content, function (key, post) {
        let marksTotal = 0;
        $.each(post.marks, function(key, mark){
          if (mark.markStatus == 1) {
            marksTotal++
          } else {
            marksTotal--
          }
        })

        if(marksTotal > localStorage.getItem("marksOfTheDay")){
          localStorage.setItem("marksOfTheDay", marksTotal)
          localStorage.setItem("postOfTheDay", post.id)
        }

        let tagsNames = [];
        $.each(post.tags, function (key, tag) {
          tagsNames.push(tag.name)
        });

        $("#postView").append(
          `
          <div  class="postsBody col-lg-6 col-md-12"></p>
            <div class="view overlay rounded z-depth-1-half mb-3">
            <img src="${post.image != null ? (IMAGE_URL + post.image) : ""}" class="img-fluid"">
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
            <h4 >
              <a href="#">
                <strong id = "postViewId${post.id}">${post.title}</strong>
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

function showPostOfTheDay(postOfTheDayId) {
  $.ajax({
    url: "http://localhost:8080/posts/" + postOfTheDayId,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let postOfTheDay = serverResponse.responseJSON

      let postMarksSize = 0;
      $.each(postOfTheDay.marks, function(key, mark){
        if (mark.markStatus == 1) {
          postMarksSize++
        } else {
          postMarksSize--
        }
      })

      let tagsNames = [];
        $.each(postOfTheDay.tags, function (key, tag) {
          tagsNames.push(tag.name)
        });

      $("#postOfTheDay").append(`
      <div class="author">
      <!-- <img style="width: 4rem;" src="images/img3.jpeg" alt="" class="d-flex mr-3"> -->
      <a>
          <p><strong>Author</strong></p>
          <h4 class="mt-0 mb-1 font-weight-bold">
              ${postOfTheDay.user.firstName} ${postOfTheDay.user.lastName}
          </h4>
      </a>
      <hr>
      <div class="media-header">
          <a>
              <h4 class="mt-0 mb-1 font-weight-bold">
                  ${postOfTheDay.title}
              </h4>
          </a>
          <br>
          <div class="media-body">
              <a class="mt-0 mb-1 font-weight">
                  ${postOfTheDay.description}
              </a>
          </div>
      </div>
      <hr>
      <div class="card-footer">
          <!-- лайки і теги -->
          <div class="media-likes row">
              <!-- лайки -->
              <div class="media-likes red-text col">
                  <h6><strong>Likes <p><i class="fas fa-heart"></i> ${postMarksSize}</p></strong>
                      
                  </h6>
              </div>
          </div>
          <!-- теги -->
          <div class="media-tags col">
              <a href="#" class="light-blue-text">
                  <h6>
                      <p><strong>Tags:</strong></p>
                      <i class="fas fa-tags"></i>
                      <strong>${tagsNames}</strong>
                  </h6>
              </a>
          </div>
      </div>
      `)
    }
  });
}

function showPostById(postId) {
  let IMAGE_URL = "http://localhost:8080/users/image?imageName=";
  $.ajax({
    url: "http://localhost:8080/posts/" + postId,
    method: "GET",
    contentType: "application/json",
    complete: function (serverResponse) {
      let postById = serverResponse.responseJSON;
      console.log(postById)

      let tagsInPost = postById.tags
      let userByPost = postById.user
      let commentsByPost = postById.comments
      let marksByPost = postById.marks

      let commentsSize = 0;
      commentsByPost.forEach(function () {
        commentsSize++
      });

      let postMarksSize = 0;
      $.each(marksByPost, function(key, mark){
        if (mark.markStatus == 1) {
          postMarksSize++
        } else {
          postMarksSize--
        }
      })

      $("#postImage").append(
        `
           
      <img src="${postById.image != null ? (IMAGE_URL + postById.image) : ""}" class="img-fluid"">
      
      <div id = "postImage${userByPost.id}" class="input-group m-2">
       <div class="custom-file">
         <input type="file" class="custom-file-input" id="image-${postById.id}" aria-describedby="inputGroupFileAddon01">
         <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
       </div>
      `
      );
      
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
      
      $("#postTotalMarks").append(
        `<h5><i class="fas fa-heart"></i><strong>Likes ${postMarksSize}</strong></h5>  
        </p>
        `
      );

      $("#postCardFooterLike").append(
        `
          <button id="postLike-${postById.id}" type="button" class="btn btn-outline-danger waves-effec p-2"><i
                  class="far fa-thumbs-up" aria-hidden="true"></i></button>
         
        </p>
        `
      );

      $("#postCardFooterDislike").append(
        `
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
                <h5 class="mt-0 font-weight-bold"><strong id="autorPost${userByPost.id}" >${userByPost.firstName} ${userByPost.lastName}<strong></h5>
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
        $("#commentCardBody").append(
          `
        

        <div class="media d-block d-md-flex mt-4">
        <img src="${comments.user.image != null ? (IMAGE_URL + comments.user.image) : ""}" class="d-flex mb-3 mx-auto"">
            <div class="media-body text-center text-md-left ml-md-3 ml-0">
                <h5 class="mt-0 front-weight-bold"><strong id="autorPost${comments.user.id}">${comments.user.firstName} ${comments.user.lastName}</strong>
                    
                </h5>
                ${comments.body}
            </div>
            
            <div class="media-likes red-text row">
            <h5><i class="fas fa-heart"></i>Likes ${marksValue}</h5>
            <div id="commentCardFooterLike" class="media-likes red-text">
            <button id="postLike-${comments.id}" type="button" class="btn btn-outline-danger waves-effec p-2"><i
            class="far fa-thumbs-up" aria-hidden="true"></i></button>
            </div>
            <div id="commentCardFooterDislike" class="media-likes red-text">
            <button id="postDislike-${comments.id}" type="button" class="btn btn-outline-danger waves-effec p-2"><i
                  class="far fa-thumbs-down" aria-hidden="true"></i></button>
          </div>
         </div>
        </div>
      
      
        `
        );
      });
      if(localStorage.getItem("authorized_userId") != userByPost.id) {
        $("#postImage"+userByPost.id).hide();
      }

    }
  });

  
}

function createComment(userId, postId) {

    let comment = {
      body : $("#commentBodyCreating").val()
    }

    $.ajax({
      url: "http://localhost:8080/comments/" + userId + "/post/" + postId,
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(comment),
      complete: function (serverResponse) {
        console.log(serverResponse.responseJSON)
        if(serverResponse.status == 201) {
          location.reload();
          
        }
      }
    })
    return false;
  }

  function showAllComments() {
     
    $.ajax({
      url: "http://localhost:8080/comments",
      method: "GET",
      contentType: "application/json",
      complete: function (serverResponse) {
        let comments = serverResponse.responseJSON
        console.log(comments)
        localStorage.setItem("commentMarksOfTheDay", 0)
        

        $.each(comments, function(key, comment){
          let marksTotal = 0;
          $.each(comment.marks, function (key, mark) {
            if (mark.markStatus == 1) {
              marksTotal++
            } else {
              marksTotal--
            }
          })

          if (marksTotal > localStorage.getItem("commentMarksOfTheDay")) {
            localStorage.setItem("commentMarksOfTheDay", marksTotal)
            localStorage.setItem("commentOfTheDayId", comment.id)
          }
        })
        
      }
    })
  }

  function showCommentOfTheDay(commentOfTheDayId) {
    let IMAGE_URL = "http://localhost:8080/users/image?imageName=";
    $.ajax({
      url: "http://localhost:8080/comments/" + commentOfTheDayId,
      method: "GET",
      contentType: "application/json",
      complete: function (serverResponse) {
        let comment = serverResponse.responseJSON;

        $("#commentOfTheDay").append(`
        <div class="media-header">
            <img style="width: 4rem;" src="${comment.user.image != null ? (IMAGE_URL + comment.user.image) : ""}" alt="" class="d-flex mr-3">
            <a>
                <h4 class="mt-0 mb-1 font-weight-bold">
                    ${comment.user.firstName}, ${comment.user.lastName}.
                </h4>
            </a>
            <div class="media-body">
                <a>
                    ${comment.body}
                </a>
            </div>
            <hr>
            <!-- лайки -->
            <div class="card-footer">
                <div class="media-likes row">
                    <!-- лайки -->
                    <div class="media-likes red-text col">
                        <h6><strong>Likes</strong>
                            <p><i class="fas fa-heart"></i> ${localStorage.getItem("commentMarksOfTheDay")}</p>
                        </h6>
                    </div>
                </div>
            </div>
        </div>
        `)
      }
    })
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



