
function toast({ title = '', message = '', type = "success", duration = 3000 }) {
    const main = document.getElementById("toast");
    console.log(main)
    if (main) {
        const toast = document.createElement('div');
        toast.style.animation = `slideInLeft ease .5s , fadeOut linear 1s ${duration/1000}s forwards`
        const icons = {
            success:'fa-solid fa-circle-check',
            error:'fa-solid fa-circle-exclamation',
            warning:'fa-solid fa-triangle-exclamation'
        }

        const icon = icons[type];

        toast.classList.add("toastMessage",`${type}`);
        toast.innerHTML = `
        <div class="toastIcon">
        <i class="${icon}"></i>
        </div>
         <div class="toastBody">
        <h3>${title}</h3>
        <p>${message}</p>
         </div>
         <div class="toastClose">
        <i class="fa-solid fa-xmark"></i>
         </div>
        `;
        main.appendChild(toast);
        setTimeout(function() {
            main.removeChild(toast)
        },duration+1000)
    }
}




function showSuccesToast () {
    toast({
        title: 'Success',
        message: 'Show toast message',
        type: 'success',
        duration: 3000
    })
}

function showErrorToast () {
    toast({
        title: 'Success',
        message: 'Show toast message',
        type: 'error',
        duration: 3000
    })
}

function showWaringToast () {
    toast({
        title: 'Success',
        message: 'Show toast message',
        type: 'warning',
        duration: 3000
    })
}

