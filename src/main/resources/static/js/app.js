const profile = document.querySelector(".profile");
const profile_menu = document.querySelector(".profile_menu");

profile.addEventListener("click",()=>{
    profile_menu.classList.toggle("show_menu");
})

const close = document.querySelector(".close_filter");
const filters = document.querySelector('.filters');
filters.style.width = "0px";
filters.style.visibility="hidden";
close.addEventListener('click',(e)=>{
    if(filters.offsetWidth>0){
        filters.style.width = "0px";
        close.innerHTML = "filter_list";
        filters.style.visibility="hidden";
    }
    else if(filters.offsetWidth<=0){
        filters.style.width = "190px";
        close.innerHTML = "close";
        filters.style.visibility="visible";
    }
})