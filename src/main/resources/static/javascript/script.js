function handleTable()
{
	const table=document.getElementById('mytable');
	const row=table.getElementsByTagName('tr');
	if(row.length>0)
	{
		table.classList.remove("hide")
	}
	else{
		Swal.fire("no items to display")
	}
	
}

function amountHandler(button)
{
   const row= button.closest('tr');
   const amount=row.cells[7].innerText;

   if(amount==''|| amount==null )
   {
       alert("Amount should not be empty");
       return;
   }
   $.ajax({
       url:"/client/create_order",
       data:JSON.stringify({amount:amount,info:'order_request'}),
       contentType:'application/json',
       type:'post',
       dataType:'json',
       success:function (res)
       {
           console.log(res)
       },
       error:function (error)
       {
           console.log(error)
       }

   })


}

function takeAction(button) {
   var row= button.closest('tr');
   console.log(row)


    Swal.fire({
        text: "Amount For The Client",
        showConfirmButton: true,
        confirmButtonText: "Send To Client",
        html: `
             <table >
             
             
            <tr >
            <td>Amount:</td>
            <td><input type="text" id="amount" required  ></td>
            </tr>
            
			<tr>
            <td>Notes:</td>
            <td><input type="text" id="notes" required  ></td>
            </tr>
            
            
            </table>
        `
    }).then((results)=>{
        if(results.isConfirmed)
        {
            var details={
                taskId:row.cells[0].innerText,
                amount:document.getElementById("amount").value,
                notes:document.getElementById("notes").value
            }
            sendToClient(details)

        }

    })

    function sendToClient(data)
    {
        $.ajax({
            url:"/manager/client-amount",
            type:"post",
            data:JSON.stringify(data),
            contentType: "application/json",
            success:function (success){
                console.log(success)
            }
        })
    }
}


function fileOperations(button) {
    const fileNameForUser = button.querySelector('a').textContent;
    var spanElement = button.querySelector('.hidden-data');


    Swal.fire({
        title: "file Operations",
        text: fileNameForUser,
        showConfirmButton: true,
        confirmButtonText: "Download File",
        showDenyButton: true,
        denyButtonText: "Delete file",
        showCancelButton: true,
    }).then((results) => {
        if (results.isConfirmed) {
            downloadFile(spanElement.textContent)
        } else if (results.isDenied) {
            Swal.fire({
                icon: "error",
                title: "are you sure you want to delete this file",
                showConfirmButton: true,
                confirmButtonText: "Yes",
                showDenyButton: true,
                denyButtonText: "No"
            }).then((results) => {
                if (results.isConfirmed) {
                    Swal.fire("file deleted successfully")
                } else {
                    Swal.fire("file is safe")
                }

            })
        }
    })

    function downloadFile(filename) {
        $.ajax({
            type: "get",
            url: "http://localhost:8080/files/download/" + encodeURIComponent(filename),
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data) {

                if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                    // For Microsoft Internet Explorer and Microsoft Edge (prior to version 18)
                    window.navigator.msSaveOrOpenBlob(data, fileName);


                } else {
                    // For other modern browsers
                    var url = window.URL.createObjectURL(new Blob([data]));
                    var a = document.createElement('a');
                    a.href = url;
                    a.target = "_blank"; // To open the download in a new tab
                    a.download = fileNameForUser; // Use the provided file name for the downloaded file
                    document.body.appendChild(a);
                    a.click();
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(a);
                }
            },
        })
    }


}


function handleViewTask(button) {
    const row = button.closest('tr');
    const query = row.cells[8].innerText
    if (row.cells[9].innerText == "FAILED") {
        Swal.fire({
            text: "Message From Your Manager",
            html: `
		
			<html>
			  <head>
			    <title>Message from Your Manager</title>
			    
			  </head>
			   
			  <body>
			    <h3>Message from Your Manager</h3>
			    <form>
			      <textarea rows="4" cols="50" id="message" readonly="readonly" style="height:100px"></textarea>
			    </form>
			
			    
			  </body>
			</html>
			`,
            didOpen: () => {
                document.getElementById('message').value = query;

            },
            showConfirmButton: true,
            confirmButtonText: "send again ",
            showDenyButton: true,
            denyButtonText: "Cancel"

        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Edit Details",
                    html: `
                 <table >
			<tr class="hide">
            <td>ID:</td>
            <td><input type="text" id="editedId"  value="${row.cells[0].innerText}" ></td>
            </tr>
            <tr >
            <td>TaskType:</td>
            <td><input type="text" id="editedTaskType" value="${row.cells[1].innerText} "  ></td>
            </tr>
			<tr>
            <td>Counts:</td>
            <td><input type="text" id="editedCount" value="${row.cells[2].innerText}"  ></td>
            </tr>
            <tr>
            <td>Requirements:</td>
            <td><input type="text" id="editedrequirements"   value="${row.cells[3].innerText}"  ></td>
            </tr>
            <tr >
            <td >RefrenceStyle:</td>
            <td><input type="text" id="editedStyle"  value="${row.cells[4].innerText}"  ></td>
            </tr>
            <tr>
            <td>DeadLine:</td>
            <td><input type="date" id="editedDeadLine"   value="${row.cells[5].innerText}"  ></td>
            </tr>
            <tr>
            <td>Charges:</td>
            <td><input type="text" id="editedCharges"   value="${row.cells[6].innerText}"  ></td>
            </tr>
            </table>
           
                `
                }).then((result) => {
                    if (result.isConfirmed) {
                        var editedData = {

                            id: document.getElementById('editedId').value,
                            tasktype: document.getElementById('editedTaskType').value,
                            count: document.getElementById('editedCount').value,
                            requirements: document.getElementById('editedrequirements').value,
                            referencestyle: document.getElementById('editedStyle').value,
                            deadline: document.getElementById('editedDeadLine').value,
                            charges: document.getElementById('editedCharges').value,
                        }
                        sendingAgainForReview(editedData)


                    }
                })
            }

        })
    } else {
        Swal.fire({
            title: "Edit The Details For Review",
            showConfirmButton: true,
            confirmButtonText: "Confirm",
            html: `
			<table >
			<tr class="hide">
            <td>ID:</td>
            <td><input type="text" id="editedId"  value="${row.cells[0].innerText}" ></td>
            </tr>
            <tr >
            <td>TaskType:</td>
            <td><input type="text" id="editedTaskType" value="${row.cells[1].innerText} "  ></td>
            </tr>
			<tr>
            <td>Counts:</td>
            <td><input type="text" id="editedCount" value="${row.cells[2].innerText}"  ></td>
            </tr>
            <tr>
            <td>Requirements:</td>
            <td><input type="text" id="editedrequirements"   value="${row.cells[3].innerText}"  ></td>
            </tr>
            <tr >
            <td >RefrenceStyle:</td>
            <td><input type="text" id="editedStyle"  value="${row.cells[4].innerText}"  ></td>
            </tr>
            <tr>
            <td>DeadLine:</td>
            <td><input type="date" id="editedDeadLine"   value="${row.cells[5].innerText}"  ></td>
            </tr>
            <tr>
            <td>Charges:</td>
            <td><input type="text" id="editedCharges"   value="${row.cells[6].innerText}"  ></td>
            </tr>
            </table>
			
			`
        }).then((result) => {
            if (result.isConfirmed) {
                var editedData = {

                    id: document.getElementById('editedId').value,
                    tasktype: document.getElementById('editedTaskType').value,
                    count: document.getElementById('editedCount').value,
                    requirements: document.getElementById('editedrequirements').value,
                    referencestyle: document.getElementById('editedStyle').value,
                    deadline: document.getElementById('editedDeadLine').value,
                    charges: document.getElementById('editedCharges').value,
                }
                sendingAgainForReview(editedData)

            }

            //for ajax calling
        })
    }

    function sendingAgainForReview(data) {
        $.ajax({
            type: "post",
            url: "/client/sending-again",
            data: data,
            success: (res) => {
                if (res == "ACCEPTED") {
                    Swal.fire({
                        icon: "success",
                        text: "success"

                    }).then((result) => {
                        if (result.isConfirmed) {
                            console.log("in reload")
                            location.reload()
                        }
                    })
                } else {
                    Swal.fire({
                        title: "success",
                        text: "success"
                    })

                }

            }
        })

    }
}


function viewReason(button) {
    const row = button.closest('tr');
    const query = row.cells[8].innerText;
    console.log(row)
    Swal.fire({
        text: "warning",
        html: `
		<html>
			  <head>
			    <title>Reason For Rejecting The Task</title>
			    
			  </head>
			   
			  <body>
			    <h3>Reason For Rejecting The Task</h3>
			    <form>
			      <textarea rows="4" cols="50" id="message" readonly="readonly" style="height:100px"></textarea>
			    </form>
			
			    
			  </body>
			</html>
		`,
        didOpen: () => {
            // Set the message content in the textarea
            document.getElementById('message').value = query;

        }

    })
}


function viewClientTask(button) {
    const row = button.closest("tr");
    console.log(row)

    Swal.fire({
        title: "success",
        html: `
        <table >
			<tr class="hide">
            <td>ID:</td>
            <td><input type="text" id="editedId"  value="${row.cells[0].innerText}"  readonly="readonly" ></td>
            </tr>
            <tr >
            <td>TaskType:</td>
            <td><input type="text" id="editedTaskType" value="${row.cells[1].innerText} " readonly="readonly" ></td>
            </tr>
			<tr>
            <td>Counts:</td>
            <td><input type="text" id="editedCount" readonly="readonly" value="${row.cells[2].innerText}" readonly="readonly" ></td>
            </tr>
            <tr>
            <td>Requirements:</td>
            <td><input type="text" id="editedrequirements" readonly="readonly" value="${row.cells[3].innerText}" readonly="readonly" ></td>
            </tr>
            <tr >
            <td >RefrenceStyle:</td>
            <td><input type="text" id="editedStyle" readonly="readonly" value="${row.cells[4].innerText}" readonly="readonly" ></td>
            </tr>
            <tr>
            <td>DeadLine:</td>
            <td><input type="text" id="editedDeadLine" value="${row.cells[5].innerText}" readonly="readonly" ></td>
            </tr>
            <tr>
            <td>Charges:</td>
            <td><input type="text" id="editedCharges" value="${row.cells[6].innerText}" readonly="readonly" ></td>
            </tr>
            <tr>
            <td>client:</td>
            <td><input type="text" value="${row.cells[8].innerText}" readonly="readonly" ></td>
            </tr>
            </table>
           
           
          
        `,
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Approve',
        denyButtonText: `Raise a query`,
    }).then((result) => {
        if (result.isConfirmed) {
            var approvedData = {
                clientId: row.cells[8].innerText,
                id: document.getElementById('editedId').value,
                tasktype: document.getElementById('editedTaskType').value,
                count: document.getElementById('editedCount').value,
                requirements: document.getElementById('editedrequirements').value,
                referencestyle: document.getElementById('editedStyle').value,
                deadline: document.getElementById('editedDeadLine').value,
                charges: document.getElementById('editedCharges').value,
            }
            sendApprovedData(approvedData)


        } else if (result.isDenied) {
            Swal.fire({
                title: "sent a query to the client",
                input: "text",
                html: `
                <table >
			<tr class="hide">
            <td>ID:</td>
            <td><input type="text" id="editedId" readonly="readonly" value="${row.cells[0].innerText}"   ></td>
            </tr>
            <tr >
            <td>TaskType:</td>
            <td><input type="text" id="editedTaskType" readonly="readonly" value="${row.cells[1].innerText} "  ></td>
            </tr>
			<tr>
            <td>Counts:</td>
            <td><input type="text" id="editedCount" readonly="readonly"  value="${row.cells[2].innerText}"  ></td>
            </tr>
            <tr>
            <td>Requirements:</td>
            <td><input type="text" id="editedrequirements" readonly="readonly"  value="${row.cells[3].innerText}"  ></td>
            </tr>
            <tr >
            <td >RefrenceStyle:</td>
            <td><input type="text" id="editedStyle" readonly="readonly"  value="${row.cells[4].innerText}"  ></td>
            </tr>
            <tr>
            <td>DeadLine:</td>
            <td><input type="text" id="editedDeadLine" readonly="readonly"  value="${row.cells[5].innerText}"  ></td>
            </tr>
            <tr>
            <td>Charges:</td>
            <td><input type="text" id="editedCharges" readonly="readonly" value="${row.cells[6].innerText}"  ></td>
            </tr>
            </table>
                `,
            })
                .then((result) => {
                    console.log("hey i just fired a query")
                    var data = {
                        clientId: row.cells[8].innerText,
                        query: result.value,
                        id: document.getElementById('editedId').value,
                        tasktype: document.getElementById('editedTaskType').value,
                        count: document.getElementById('editedCount').value,
                        requirements: document.getElementById('editedrequirements').value,
                        referencestyle: document.getElementById('editedStyle').value,
                        deadline: document.getElementById('editedDeadLine').value,
                        charges: document.getElementById('editedCharges').value,
                    }
                    queryForClient(data);


                })

        }

    })

    function queryForClient(data) {
        $.ajax({
            type: "post",
            url: "/manager/query",
            data: data,
            contentType: "application/x-www-form-urlencoded",
            success: (success) => {
                if (success == "ACCEPTED") {
                    Swal.fire({
                        icon: "success",
                        text: "Query has been successfully sent to the client"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    })

                } else {
                    Swal.fire("failed")
                }


            }


        })
    }


    function sendApprovedData(data) {
        $.ajax({
            url: "/manager/approve-task",
            method: "post",
            data: data,
            contentType: "application/x-www-form-urlencoded",
            success: (response) => {
                if (response == "ACCEPTED") {
                    Swal.fire("Approved")
                    location.reload();
                } else {
                    Swal.fire("internal server error")
                }
            }
        })
    }
}

function handleInReview(button) {
    const row = button.closest("tr");
    const managerName = $("#manager_details option:selected").text();

    Swal.fire({
        title: "success",
        html: `
		<table >
			<tr class="hide">
            <td>ID:</td>
            <td><input type="text" id="editedId" value="${row.cells[0].innerText}"  readonly="readonly" ></td>
            </tr>
            <tr >
            <td>CRM:</td>
            <td><input type="text" id="editedCrm"></td>
            </tr>
			<tr>
            <td>Name:</td>
            <td><input type="text" id="editedName" readonly="readonly" value="${row.cells[1].innerText}"></td>
            </tr>
            <tr>
            <td>Email:</td>
            <td><input type="text" id="editedEmail" readonly="readonly" value="${row.cells[2].innerText}"></td>
            </tr>
            <tr class="hide" >
            <td >Password:</td>
            <td><input type="text" id="editedPassword" readonly="readonly" value="${row.cells[3].innerText}"></td>
            </tr>
            <tr>
            <td>Phone:</td>
            <td><input type="text" id="editedPhone" value="${row.cells[4].innerText}"></td>
            </tr>
            <tr>
            <td>Address:</td>
            <td><input type="text" id="editedAddress" value="${row.cells[5].innerText}"></td>
            </tr>
            <tr>
            <td>Orgname:</td>
            <td><input type="text" id="editedOrgname" value="${row.cells[6].innerText}"></td>
            </tr>
            <tr>
            <td>C.person:</td>
            <td><input type="text" id="editedPerson" value="${row.cells[7].innerText}"></td>
            </tr>
            <tr>
            <td>Rupees:</td>
            <td><input type="text" id="editedRupees" value="${row.cells[8].innerText}"></td>
            </tr>
            <tr>
            <td>Allocated to:</td>
            <td><input type="text"  id="editedManager" value="${managerName}"></td>
            </tr>
           
		`,
        showConfirmButton: true,
        confirmButtonText: "Confirm",
        preConfirm: () => {
            var formdata = {
                id: document.getElementById('editedId').value,
                crm: document.getElementById('editedCrm').value,
                name: document.getElementById('editedName').value,
                email: document.getElementById('editedEmail').value,
                password: document.getElementById('editedPassword').value,
                phone: document.getElementById('editedPhone').value,
                address: document.getElementById('editedAddress').value,
                orgname: document.getElementById('editedOrgname').value,
                contactPerson: document.getElementById('editedPerson').value,
                rupees: document.getElementById('editedRupees').value,
                managerId: $(button).closest("tr").find('.mySelect').val(),

            }

            sendDataToServer("/admin/client-data", "post", formdata)
            console.log(formdata)

        },

    })

    function sendDataToServer(url, method, formdata) {
        $.ajax({
            url: url,
            method: method,
            data: formdata,
            contentType: 'application/x-www-form-urlencoded',
            success: function (response) {
                if (response == "ACCEPTED") {

                    Swal.fire({
                        icon: "success",
                        text: "successfully allocated to the manager",
                        showConfirmButton: true,
                        confirmButtonText: "ok",
                        preConfirm: () => {
                            location.reload()
                        }
                    })


                } else {
                    Swal.fire({
                        icon: "error",
                        text: "Internal Server Error"
                    })
                }


            },
            error: function (error) {
                console.log(error)
            }
        })
    }


}


function handleSignUp(button) {
    var form = button.closest("form");
    Swal.fire({
        title: "Are You Sure",
        confirmButtonText: "Request",
        preConfirm: () => {
            var data = {
                name: form.elements["name"].value,
                email: form.elements["email"].value,
                password: form.elements["password"].value,
                phone: form.elements["phone"].value,
                address: form.elements["address"].value,
                orgname: form.elements["orgname"].value,
                contactperson: form.elements["contactperson"].value,
                rupees: form.elements["rupees"].value,
            }
            sendSignUpDetails(data);
        }

    })

    function sendSignUpDetails(data) {
        $.ajax({
            url: "/user-request",
            method: "post",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function (response) {
                if (response == "ACCEPTED") {
                    console.log(response)
                    Swal.fire({
                        icon: "success",
                        text: "you will receive a email when someone approves you"
                    })
                } else {
                    Swal.fire({
                        icon: "error",
                        text: "Error Generated From Server"
                    })
                }
            },


        })

    }


}
