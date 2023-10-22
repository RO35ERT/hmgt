const check = document.querySelectorAll(".check");
const cards = document.querySelector('.cards');
const momo = document.querySelector('.mobile');

momo.style.visibility= 'hidden';
cards.style.visibility='hidden';
check.forEach((e)=>{
    e.addEventListener('click',(i)=>{
            if(e.value==='mastercard' || e.value==='visa'){
                momo.style.visibility= 'hidden';
                cards.style.visibility='visible';
            }else{
                momo.style.visibility= 'visible';
                cards.style.visibility='hidden';
            }
    })
})