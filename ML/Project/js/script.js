'use strict';
let liveSelected=true;
function optionPresed(){
  if(liveSelected)
  {
    document.querySelector('.live-choices').style.display="none";
    document.querySelector('.y2019-choices').style.display="block";
    liveSelected=false;
    document.getElementById("live").className = "button-style";
    document.getElementById("y2019").className = "button-style-selected";
    liveSelected=false;
  }else {
    document.querySelector('.live-choices').style.display="block";
    document.querySelector('.y2019-choices').style.display="none";
    document.getElementById("y2019").className = "button-style";
    document.getElementById("live").className = "button-style-selected";
    liveSelected=true;
  }
}
/*  const data = await d3.csv('datat.csv');
  // do whatever with data here
  console.log(data[1]);

}

doThings();*/
let Data=null;
d3.csv('datat.csv', function(error, data) {
  if (error) throw error;
  Data=data;
  console.log(data[0][0]);
  console.log(data[0]);
  console.log(data[1]);

});
//console.log(Data[1]);
let dat=[{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jan", Label: "pos"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jun", Label: "pos"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jan", Label: "pos"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Mar", Label: "Neg"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jan", Label: "pos"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Mar", Label: "Neg"},
{CreatedAt: "Mar", Label: "pos"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jun", Label: "pos"}, {CreatedAt: "Jun", Label: "Neg"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jun", Label: "pos"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jun", Label: "Neg"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Aug", Label: "pos"}, {CreatedAt: "Aug", Label: "pos"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Aug", Label: "Neg"},
{CreatedAt: "Aug", Label: "Neg"}, {CreatedAt: "Aug", Label: "pos"},
{CreatedAt: "Jan", Label: "Neg"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Jun", Label: "Neg"}, {CreatedAt: "Jan", Label: "Neg"},
{CreatedAt: "Nov", Label: "pos"}, {CreatedAt: "Nov", Label: "pos"},
{CreatedAt: "Nov", Label: "Neg"}, {CreatedAt: "Nov", Label: "Neg"},
{CreatedAt: "Nov", Label: "Neg"}, {CreatedAt: "Nov", Label: "pos"}]
var entries = d3.nest()
    .key(function(d) { return d.CreatedAt; })
    .key(function(d) { return d.Label; })
    .entries(Data);
console.log(entries);
var Monthes = entries.map(a => a.key);
var values = entries.map(a => a.values);
console.log(Monthes);
console.log(values);
/*var lab = d3.nest()
    .key(function(d) { return d.Neg; })
    .entries(values);
console.log(lab);*/
//var valuesN = values.map(a => a.Neg);
//console.log(valuesN);


var valsP=[];
var valsN=[];
for(var item of values){
  for(var i of item)
  {
    console.log(i);
    if (i.key=="0") {
      valsN.push(i.values.length);
      console.log(i.values.length);
    }else {
      valsP.push(i.values.length);
    }
  }

}
console.log(valsN);
console.log(valsP);
var ctx = document.getElementById("chart-0").getContext("2d");
/*
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
            label: '# of Votes',
            data: [0, 1, 0, 1, 1, 0],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});
*/
new Chart(ctx, {
type: 'line',
data: {
labels: Monthes,
datasets: [{
label: "انخفاض",
fill: false,
backgroundColor: window.chartColors.red,
borderColor: window.chartColors.red,
data:valsN,
}, {
label: "ارتفاع",
fill: false,
backgroundColor: window.chartColors.green,
borderColor: window.chartColors.green,
//borderDash: [5, 5],
data: valsP,
}]
},
options: {
responsive: true,
title:{
display:true,
text:'Line Chart'
},
tooltips: {
mode: 'index',
intersect: false,
},
hover: {
mode: 'nearest',
intersect: true
},
scales: {
xAxes: [{
  display: true,
  scaleLabel: {
    display: true,
    labelString: 'Month'
  }
}],
yAxes: [{
  display: true,
  scaleLabel: {
    display: true,
    labelString: 'Value'
  }
}]
}
}
});
