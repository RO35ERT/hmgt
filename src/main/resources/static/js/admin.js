const cancel = document.querySelectorAll(".cancel");
const form_update = document.querySelector(".update_form");
const update = document.querySelectorAll(".update");
cancel.forEach((e)=>{
    e.addEventListener('click',()=>{
        form_update.classList.remove("show_update");
    })
})

const roomNumber = document.querySelector("#roomnumber");
const roomType = document.querySelector("#room_type");
const price = document.querySelector("#price");
const ammenities = document.querySelector("#ammenitiesValue");



update.forEach((e)=>{
    e.addEventListener('click',()=>{
        form_update.classList.add("show_update");
        const parent = e.parentNode.parentNode;
        const room_price = parent.querySelector(".room_room_price");
        const room_type = parent.querySelector(".room_room_type");
        const room_number = parent.querySelector(".room_roomNumber");
        const roomAmmenities = document.querySelector("#ammenities");

        roomType.value = room_type.innerHTML;
        roomNumber.value = room_number.innerHTML;
        price.value = room_price.innerHTML;
        roomAmmenities.value = ammenities.value;
    })
})


const all_dates = document.querySelectorAll(".for_month");

all_dates.forEach((e)=>{
    let myForm = e.parentElement;
    e.form.addEventListener('change',(i)=>{
        const income = document.querySelector("#income");
        const pendingReserves = document.querySelector("#pending_reserves");
        const reserves = document.querySelector("#reserve");
        async function getDate() {
            const response = await fetch(`http://localhost:8080/getIncomePerMonth/${e.value}`);
                const data = await response.json();
                income.innerHTML= data;
        }
        async function getPendingReserveDate() {
            const response = await fetch(`http://localhost:8080/getPendingReservePerMonth/${e.value}`);
            const data = await response.json();
            pendingReserves.innerHTML= data;
        }

        async function getReserveDate() {
            const response = await fetch(`http://localhost:8080/getReservePerMonth/${e.value}`);
            const data = await response.json();
            reserves.innerHTML= data;
        }



        if(e.getAttribute('id')==="for_pending_reserves"){
            getPendingReserveDate();
        }
        if(e.getAttribute('id')==="for_income"){
            getDate();
        }
        if(e.getAttribute('id')==="for_reserves"){
            getReserveDate();
        }

    });
});

const add_branch = document.querySelector(".add_branch");
const close_branch = document.querySelector(".close_branch");

close_branch.addEventListener('click',()=>{
    add_branch.classList.remove("show_branch");
})

const branch = document.querySelector(".branch");

branch.addEventListener('click',()=>{
    add_branch.classList.add("show_branch");
})











