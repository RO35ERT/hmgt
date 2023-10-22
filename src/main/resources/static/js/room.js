
const all = document.querySelectorAll(".check");
const reserve = document.querySelector(".reserve");
const single_date = document.querySelector(".start_date");


const checkCheckBox = ()=>{
    let isChecked = false;
    all.forEach((e)=>{
        e.addEventListener('change',(i)=>{
            if(e.checked){
                isChecked = true;
            }
        })
    })
    return isChecked;
}
//validating dates
const checkin = document.querySelector("#checkin");
const checkout = document.querySelector("#checkout");
const total = document.querySelector(".total");
const numberOfDays = document.querySelector(".nod");
reserve.addEventListener("click",(e)=>{
    if(checkin.value ==="" || checkout.value ===""){
        e.preventDefault();
        alert("Please choose dates");
    }else{
    }
})

//setting start date to current
const curDate = new Date();
const curDateToString = curDate.toISOString().split('T')[0];
let curDateFromDb = new Date(single_date.value.toLocaleString());

//checking if the latest date for the checkout exist and setting it to it
if(single_date.value === ''){
    checkin.min = curDateToString
}else{
    checkin.min = curDateFromDb.toISOString().split('T')[0];
}
checkout.min = checkin.min;

const inputs = [checkout, checkin];

const totalPrice = document.querySelector(".pricePerDay");
const totalAmount = document.querySelector(".totalAmount");

//calculating number of days, prices and tax
inputs.forEach((e)=>{
    e.addEventListener('change',()=>{
        const startDate = new Date(checkin.value);
        const endDate = new Date(checkout.value);

        if(e.id === "checkin"){
            checkout.min = checkin.value;
        }
        const differenceInMillis= endDate-startDate;

        const differenceInDays = differenceInMillis/(1000*60*60*24);


        if(differenceInDays === 0 || differenceInDays ===1){
            const tax = (16/100)*Number(totalPrice.innerHTML);
            const priceAndTax = Number(totalPrice.innerHTML) + tax;
            numberOfDays.innerHTML = 1 + " day";
            total.innerHTML = String(priceAndTax);
            totalAmount.value = priceAndTax;
        }else{
            numberOfDays.innerHTML = differenceInDays + " days";
            const totals = differenceInDays * Number(totalPrice.innerHTML);
            const tax = (16/100)*totals;
            const priceAndTax = totals + tax;
            total.innerHTML = String(priceAndTax);
            totalAmount.value = priceAndTax;
            console.log(totalAmount.value)
        }
    })
});

//setting ammenities


const ammenities = document.querySelector("#ammenities").value;

const ammenitiesList = document.querySelector(".ammenities_list");

const ammenities_array = ammenities.split(",");

ammenities_array.forEach((e)=>{
    const listItem = document.createElement('li');
    listItem.innerHTML = e;
    ammenitiesList.appendChild(listItem);
})