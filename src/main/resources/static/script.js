$(document).ready(function() {
    $('button').on('mousedown',
        function (event) {
            event.preventDefault();
        }
    );
});

document.getElementById("sent").textContent = " "
function openM(id) {
    document.getElementById(id).style.display = "block";
}
function closeM(id) {
    document.getElementById(id).style.display = "none";
}
function openModal(id) {
    $('#'+id).fadeIn('fast');
}
function closeModal(id) {
    $('#'+id).fadeOut('fast');
}
function changeStatus(job) {
    $.post('/work')
    let balance = parseInt(document.getElementById("char_balance").textContent, 10)
    document.getElementById("char_status").textContent = job.toString();
    setTimeout( () => {
        document.getElementById("char_status").textContent = "Idle";
        if(job.toString() === "Doing lawyer stuff...") {
            balance += 20000
            document.getElementById("char_balance").style.color = "green"
            document.getElementById("char_balance").textContent = balance.toString()
            setTimeout( () => {
                document.getElementById("char_balance").style.color = "whitesmoke"
            }, 1000);
        } else if(job.toString() === "Building things...") {
            balance += 10000
            document.getElementById("char_balance").style.color = "green"
            document.getElementById("char_balance").textContent = balance.toString()
            setTimeout( () => {
                document.getElementById("char_balance").style.color = "whitesmoke"
            }, 1000);
        }
    }, 2500);
}
function unSub() {
    let subs = document.getElementById("char_sub").textContent
    if(subs === "Not subscribed") {
        swal({
            title: "You have already unsubscribed",
            icon: "warning",
            button: "Ok"
        })
        return
    }
    document.getElementById("char_sub").textContent = "Not subscribed";
}
function sub() {
    let subs = document.getElementById("char_sub").textContent
    if(subs === "Subscribed") {
        swal({
            title: "You have already subscribed",
            icon: "warning",
            button: "Ok"
        })
        return
    }
    document.getElementById("char_sub").textContent = "Subscribed";
}

document.getElementById("radio_btn").addEventListener("click", () => {
    document.getElementById("char_status").textContent = "Switching radio...";
    setTimeout( () => {
        document.getElementById("char_status").textContent = "Idle"
    }, 1750);
});

if(document.getElementById("char_status").textContent !== "Idle") {
    setTimeout( () => {
        document.getElementById("char_status").textContent = "Idle"
    }, 4000);
}

$('#postForm').submit(function(e){
    e.preventDefault();
    $.ajax({
        url: '/post_msg',
        type: 'post',
        data:$('#postForm').serialize(),
        success:function(){
            document.getElementById("sent").textContent = "Message delivered successfully!"
            setTimeout( () => {
                document.getElementById("sent").textContent = " "
            }, 1500);
        }
    });
});

$('#unsubForm').submit(function(e){
    e.preventDefault();
    $.ajax({
        url: '/unsub',
        type: 'post',
        data:$('#unsubForm').serialize(),
        success:function(){
            document.getElementById("unsub_block").style.display = "none"
        }
    });
});

/*const x = document.getElementById("myAudio");
x.loop = true;

function startRadio(state) {
    x.muted = state !== "On";
}

function turnRadio() {
    $.post('/radio')
    x.muted = !x.muted;
}*/