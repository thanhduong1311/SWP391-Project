$("#evoCalendar").evoCalendar({theme: 'Midnight Blue',calendarEvents: []});


$("#evoCalendar").evoCalendar('addCalendarEvent', [
    // {
    //   id: '09nk7Ts',
    //   name: "My Birthday",
    //   date: "February/15/2020",
    //   type: "birthday",
    //   everyYear: true
    // }
]);




//
var s = document.getElementById('data').innerHTML;
var events = JSON.parse(s);

$("#evoCalendar").evoCalendar('addCalendarEvent', events);

$("#evoCalendar").on('selectEvent', function(event) {
    console.log(event);
});



var dayBtn = document.getElementsByClassName("evo-calendar calendar-initialized midnight-blue");

for (var i =0;i<dayBtn.length;i++) {
    dayBtn[i].addEventListener("click",()=>{
        document.getElementById("activeDate").value =
            document.getElementsByClassName("day calendar-active")[0].getAttribute("data-date-val")
    })
}
