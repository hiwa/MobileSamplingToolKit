//<![CDATA[ 
$(document).ready(function () {  
    
    
    
});


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




















//                        alert("yo");
//
//                        $('.submitlink').click(function(){
//                            alert("hey!");
//                            var ext = $('.file').val().split('.').pop().toLowerCase();
//                            alert(ext);
//                            if(ext.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
//                                alert('invalid extension!');
//                            }
//                        }
    
//        var firstname = $("input[id$='firstname']");
//        var lastname = $("input[id$='lastname']");
//        var username = $("input[id$='username']"); 
//        var password = $("input[id$='password']"); 
//        var passwordconfirm = $("input[id$='passwordconfirm']"); 
//        var email = $("input[id$='email']"); 
//
//        if(username.val().length==0 || password.val().length==0 ||passwordconfirm.val().length==0 ||email.val().length==0 ||firstname.val().length==0 ||lastname.val().length==0){
//            $("#validationmessage").text("Please fill all required fields of first and last name, username, password, confirm and email.");
//            return false;
//        }
//        else if (username.val().length<5 || username.val().length>40){
//            $("#validationmessage").text("Username length must be between 5 and 40 characters.");
//            return false;
//        }
//        else if (password.val() != passwordconfirm.val()){
//            $("#validationmessage").text("Password and Confirm Password fields do not match.");
//            return false;
//        }
//        else if (!IsEmail(email.val())){
//            alert(email.val());
//            $("#validationmessage").text("Please format your email e.g.  johnsmith@company.com");
//            return false;
//        }
//        else{
//            $("#validationmessage").text().clear();
//            return true;
//        }
//                        });
//
//                        function IsEmail(email) {
//                            var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//                            return regex.test(email);
//                        }
//                    });
//]]>



