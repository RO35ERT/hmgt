const firstName = document.querySelector("#firstName");
const lastName = document.querySelector("#lastName");
const email = document.querySelector("#email");
const postalAddress = document.querySelector("#postalAddress");
const residential = document.querySelector("#residentialAddress");
const password = document.querySelector("#password");
const confirmPass = document.querySelector("#comfirmPassword");
const register = document.querySelector("#submit");

const form = document.querySelector(".form-control");

register.addEventListener("click",(e) => {
    e.preventDefault();
    let data = [firstName,lastName,
        email,password,postalAddress,confirmPass,residential];
    let count = 0;
    for(let i =0;i<data.length;i++){
        let temp = data[i].value.trim();
        if(temp === ""){
            count +=1;
        }
    }
    if(count===0) {
        if(!checkPass(data[3].value,data[5].value)){
            alert("Password do not match");
        }
    }else{
        alert("One or more field is empty")
    }
})

function checkPass(pass, confPass) {
    if(pass ===confPass){
        if(pass.length>=8){
            return true;
        }
    }else{
        return false;
    }
}