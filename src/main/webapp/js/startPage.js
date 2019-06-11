/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
window.addEventListener("load", function (event) {
    var toggler = document.getElementsByClassName('treeChild');
    console.log(toggler);
    for (var i = 0; i < toggler.length; i++) {
        console.log(i);
        toggler[i].addEventListener("click", function () {
            this.parentElement.querySelector(".treeNested").classList.toggle("active");
            //this.classList.toggle("treeChild-open");
        });
    }
});

function openTab(evt, testbed) {

    var i, tabcontent, tabbutton;

    tabcontent = document.getElementsByClassName("tabcontent");

    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tabbutton = document.getElementsByClassName("tabbutton");
    for (i = 0; i < tabbutton.length; i++) {
        tabbutton[i].className = tabbutton[i].className.replace(" active", "");
    }

    document.getElementById(testbed).style.display = "block";
    evt.currentTarget.className += " active";
}
function updateList() {
    alert("hello");
    updaterT();
}