
function toast( { title = '', message = '', type = "success", duration = 3000 }) {
    const main = document.getElementById("toast");
    if (main) {
        const toast = document.createElement('div');
        toast.onclick = function (e) {
            if (e.target.closest('.toastClose')) {
                main.removeChild(toast);
            }

        };
        toast.style.animation = `slideInLeft ease .5s , fadeOut linear 1s ${duration / 1000}s forwards`;
        const icons = {
            success: 'bi bi-check-circle-fill',
            error: 'bi bi-exclamation-circle-fill',
            warning: 'bi bi-exclamation-triangle-fill'
        };

        const icon = icons[type];

        toast.classList.add("toastMessage", `${type}`);
        toast.innerHTML = `
        <div class="toastIcon">
        <i class="${icon}"></i>
        </div>
         <div class="toastBody">
        <h3>${title}</h3>
        <p>${message}</p>
         </div>
         <div onclick="closeMessage()" class="toastClose">
        <i class="bi bi-x"></i>
         </div>
        `;
        main.appendChild(toast);
        setTimeout(function () {
            main.removeChild(toast);
        }, duration + 1000);
    }
}



function showSuccesToast (mes) {
    toast({
        title: 'Success',
        message: mes,
        type: 'success',
        duration: 3000
    })
}

function showErrorToast (mes) {
    toast({
        title: 'Failed',
        message: mes,
        type: 'error',
        duration: 3000
    })
}

function showWaringToast (mes) {
    toast({
        title: 'Warning',
        message: mes,
        type: 'warning',
        duration: 3000
    })
}


function splitMessase(s) {

    if (s != "") {
        var sts = s.slice(0, s.indexOf("#"));
        var mes = s.slice(s.indexOf("#") + 1, s.length);

        if(sts == 'Success') {
            showSuccesToast(mes)
        }
        if(sts =='Failed' ) {
            showErrorToast(mes)
        }
        if(sts == 'Warning') {
            showWaringToast(mes)
        }
    }
}