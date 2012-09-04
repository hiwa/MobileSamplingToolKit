//<![CDATA[ 
$(document).ready(function() {
    //$('#welcomeMsg').delay(800).fadeOut(400);
    // $("#validationmessage").text();
    $('.username').val('');
    // alert("page loaded!");
    // $("#validationmessage").text().clear();
    $('.lbl').delay(1000).fadeOut("slow");
// alert("test");
// $('.username').textContenttext().clear();

}); 

$("p").click(function() {
    alert("test"); 
});
//]]>

$('.loginclass').click(function()

{
        var username = $('.username'); 
        var password = $('.password'); 

        //Username Pattern: should be between 5 and 16 characters from letters and digits 
        var usernameRegex = /^[a-z0-9_-]{5,16}$/;

        if (!usernameRegex.test(username.val())){
            $("#validationmessage").text("Username is too short or containning illigal characters!");
            $('.username').val('');
            $('.password').val('');
            return false;
        }
        //If there is a any reserved words like admin.
        else if (username.val() == "admin" )
        {
            $("#validationmessage").text("this name is reserved!");
            $('.lbl').text("");
            $('.username').val('');
            $('.password').val('');
            return false;
        }
        //
        else if(username.val().length==0 || password.val().length==0){
            $("#validationmessage").text("Please fill in both username and password.");
            $('.username').val(''); 
            //$('.lbl').text("");
            return false;
        }
        else if (username.val().length<5 || username.val().length>40){
            $("#validationmessage").text("Username length must be between 5 and 40 characters.");
            //$('.lbl').text("");
            return false;
        }
        else{
            $("#validationmessage").text().clear();
            // $('.lbl').text("");
            // $('.lbl').text();
            return true;
        }


    });