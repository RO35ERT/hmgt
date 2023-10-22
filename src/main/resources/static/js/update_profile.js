
const firstName = document.querySelector(".first_name");
const lastName = document.querySelector(".last_name");
const middleName = document.querySelector(".middle_name");
const residential = document.querySelector(".residential");
const postal = document.querySelector(".postal");

const update_profile = document.querySelector(".update_profile");

const update=document.querySelector(".update_form")
const profile = document.querySelector(".profile");
update_profile.addEventListener('click',(e)=>{
    e.preventDefault()
    update.style.visibility="visible";
    profile.classList.remove("show");
    document.querySelector("#first_name").value=firstName.innerHTML;
    document.querySelector("#last_name").value=lastName.innerHTML;
    document.querySelector("#middle_name").value=middleName.innerHTML;
    document.querySelector("#residential").value=residential.innerHTML;
    document.querySelector("#postal").value=postal.innerHTML;
})

const close_update = document.querySelector(".close_update");

close_update.addEventListener("click",()=>{
    update.style.visibility="hidden";
    profile.classList.add("show");
})

const change_password = document.querySelector(".change_password");

const email_message = document.querySelector(".email_message");


change_password.addEventListener('click',()=>{
    email_message.innerHTML = "An email has been sent with instructions";
})
