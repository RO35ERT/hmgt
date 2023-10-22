const showIcons = document.querySelector(".pass-icon");
const visibility = document.querySelector(".visibility");
const check = document.querySelector("#check");
const showInput = document.querySelector("#show");

showIcons.addEventListener('click',(e)=>{
    if(e.target.innerHTML === "visibility"){
        check.checked = true;
        showInput.type = "text";
        e.target.innerHTML ="visibility_off"
    }else{
        check.checked = false;
        showInput.type = "password";
        e.target.innerHTML ="visibility"
    }
    if(check.checked){
        showInput.type = "text";
    }else{
        showInput.type = "password";
    }
})

