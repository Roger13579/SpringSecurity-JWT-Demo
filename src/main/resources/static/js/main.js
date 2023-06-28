var jq = $.noConflict();
// $(document).ready(function () {
//     $("#loginform").submit(function (event) {
//         //stop submit the form, we will post it manually.
//         event.preventDefault();
//         event.stopImmediatePropagation();
//         ajax_login();
//     });
//     $("#registerform").submit(function (event) {
//         //stop submit the form, we will post it manually.
//         event.preventDefault();
//         event.stopImmediatePropagation();
//         ajax_register();
//     });
// });
// function ajax_login(){
//     var dataObj = {};
//     dataObj["email"] = $("#username").val();
//     dataObj["password"] = $("#password").val();
//     jq.ajax({
//         dataType: "json",
//         method: "POST",
//         url: "/users/auth",
//         contentType: "application/json; charset=UTF-8",
//         data: JSON.stringify(dataObj),
//         success: function (res){
//             redirect(res);
//         }
//     });
// }
//
// function redirect(res) {
//     jq.ajax({
//         dataType: "html",
//         method: "GET",
//         url: "/index",
//         headers : {
//             "Authorization":"Bearer " + res["token"]
//         },
//         contentType: "application/json; charset=UTF-8",
//         success:function (response){
//             $("html").html($("html", response).html());
//         }
//     });
// }
// function ajax_register() {
//     var dataObj = {};
//     dataObj["email"] = $("#email").val();
//     dataObj["password"] = $("#password").val();
//     dataObj["authorities"] = [$("#authorities").val()];
//     jq.ajax({
//         dataType: "json",
//         method: "POST",
//         url: "/users/register",
//         contentType: "application/json; charset=UTF-8",
//         data: JSON.stringify(dataObj),
//         success: function () {
//             alert("註冊成功");
//             window.location = "/login";
//         }
//     });
// }