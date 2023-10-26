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
var j = document.getElementById('jobs').innerHTML;
var events = JSON.parse(s);
const jobs = JSON.parse(j);

console.log(jobs);

$("#evoCalendar").evoCalendar('addCalendarEvent', events);

$("#evoCalendar").on('selectEvent', function(event) {
    console.log(event);
});



var dayBtn = document.getElementsByClassName("evo-calendar calendar-initialized midnight-blue");

for (var i =0;i<dayBtn.length;i++) {
    dayBtn[i].addEventListener("click",()=>{
        document.getElementById("activeDate").value =
          document.getElementsByClassName("day calendar-active")[0].getAttribute("data-date-val")
        var d = document.getElementById("activeDate").value
        loadDateEvents(d)
    })
}


/// render

const tbody = document.getElementById('bodyTable');


 function loadDateEvents(date) {


     const newHtml = jobs.map((item) => {
         if(item.jobInCalendar == date) {
             if(item.status == "INPROGRESS" ) {



             return `
    <tr class="fw-normal p-3" >
    <div style=" padding: 10px">
    
      <th>
        <img class="shadow-1-strong rounded-circle" alt="avatar 1" style=" width: 55px; height: auto;" src="${item.customerAvt}" />
        <span class="ms-2">${item.customerName}</span>
      </th>
      <td class="align-middle"><span>${item.serviceName}</span>
      </td>
      <td class="align-middle">
      <h6 class="mb-0"><span class="badge bg-success">${item.status}</span></h6>
      </td>
      <td class="align-middle"><a class="btn btn-outline-primary" href="/employee/job/${item.jobID}">Detail</a>
      </td>
</div>
      
    </tr>
  `;
         } else {
                 return `
    <tr class="fw-normal p-3"  style="height: 40px" xmlns="http://www.w3.org/1999/html">
     <div style=" padding: 10px">
      <th>
        <img class="shadow-1-strong rounded-circle" alt="avatar 1" style=" width: 55px; height: auto;" src="${item.customerAvt}" />
        <span class="ms-2">${item.customerName}</span>
      </th>
      <td class="align-middle"><span>${item.serviceName}</span>
      </td>
      <td class="align-middle">
      <h6 class="mb-0"><span class="badge bg-primary">${item.status}</span></h6>
      </td>
      <td class="align-middle"><a class="btn btn-outline-primary" href="/employee/job/${item.jobID}">Detail</a>
      </td>
      </div>
    </tr>
  `;
             }
         }

     });

     document.getElementById('bodyTable').innerHTML = newHtml.join('');
 }