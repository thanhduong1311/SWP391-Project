function formatNumber(number) {
    const numberString = number.toString();
    const newNumberString = numberString.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return newNumberString+" ";
}

const number = Number.parseInt(document.getElementById("amount").innerHTML);

console.log(formatNumber(number));


window.onload = function () {
    document.getElementById("amount").innerHTML = formatNumber(number)
}