const btnBusiness = document.getElementById('create-business-button');

const txtName = document.getElementById('formName');
const txtAddress = document.getElementById('formAddress');
const txtHours = document.getElementById('formHours');
const txtType = document.getElementById('formType');
const txtDescript = document.getElementById('formDescription');

btnBusiness.addEventListener('click', e => {
    let businessData = {
        name: txtName.value,
        address: txtAddress.value,
        hours: txtHours.value,
        type: txtType.value,
        description: txtDescript.value,
        num_tables: 0
    };
    let userID = firebase.auth().currentUser.uid;

    let businessID = firebase.database().ref().child('businesses').push().key;
    businessData.id = businessID;

    let updates = {};
    updates['/users/' + userID + '/businesses/' + businessID] = businessID;
    updates['/businesses/' + businessID] = businessData;

    firebase.database().ref().update(updates);
    $('#businessModal').modal('hide');
});

firebase.auth().onAuthStateChanged(firebaseUser => {
    if(firebaseUser){
        console.log(firebaseUser);
        const root = document.getElementById('root');
        var businessIdRef = firebase.database().ref('/users/' + firebaseUser.uid + '/businesses/');
        businessIdRef.on('value', (snapshot) => {
            root.innerHTML = '';
            snapshot.forEach((bizID =>{
                var business = new Business(bizID.val(), root);
            }));
        });
    }else{
        console.log("no logged in user, no businesses loaded")
    }
});


class Business {
    constructor(b_id, root) {
        //query firebase business
        var businessIdRef = firebase.database().ref('/businesses/' + b_id);
        businessIdRef.on('value', (snapshot) => {
            this.bizdata = snapshot.val();
        });
        //query firebase tables + create table obj for each
        var tableIdRef = firebase.database().ref('/table/' + b_id);
        tableIdRef.on('value', (snapshot) => {
            this.tables = [];
            snapshot.forEach((childSnapshot) => {
                var tableData = new Table(childSnapshot.val());
                this.tables.push(tableData);
            });
            if(this.node){
                this.displayTable()
            }else{
                this.node = this.display(root);
            }
        });
    }

    display(root) {
        var newBusinessNode = document.createElement('div');
        newBusinessNode.className = "business";
        root.appendChild(newBusinessNode);

        var newBusinessDisplayNode = document.createElement('div');
        newBusinessDisplayNode.className = "business-display";
        newBusinessNode.appendChild(newBusinessDisplayNode);

        var newBizTableNode = document.createElement('div');
        newBizTableNode.className = "biz-table";
        newBusinessNode.appendChild(newBizTableNode);

        newBusinessDisplayNode.innerHTML += 
        `<div class="row">
                <p class="biz-name">`+this.bizdata.name+`</p>
                <div class="col table-data-feature">
                    <button id="new-table-button-`+this.bizdata.id+`" class="item" title="new-table" data-toggle="modal" data-target="#tableModal">
                        <i class="zmdi zmdi-plus"></i>
                    </button>
                </div>
            </div>
            <div class="row">
                <p class="biz-type">`+this.bizdata.type+`</p>
            </div>
            <div class="row">
                <p class="biz-addr">`+this.bizdata.address+`</p>
            </div>
            <div class="row">
                <p class="biz-hr">`+this.bizdata.hours+`</p>
            </div>
            <div class="row">
                <p class="biz-tables" id="biz-tables-`+this.bizdata.id+`">Tables: `+this.bizdata.num_tables+`</p>
            </div>
            <div class="row">
                <p class="biz-desciption">`+this.bizdata.description+`</p>
            </div>`;
        
        const newTableButton = document.getElementById("new-table-button-"+this.bizdata.id);
        newTableButton.addEventListener('click', e=>{
            var old_btnCreateTable = document.getElementById('create-table-button');
            var new_btnCreateTable = old_btnCreateTable.cloneNode(true);
            old_btnCreateTable.parentNode.replaceChild(new_btnCreateTable, old_btnCreateTable);

            new_btnCreateTable.addEventListener('click', e=>{
                const formSeats = document.getElementById('formSeats');
                const formOutdoor = document.getElementById('formOutdoor');
                let tableData = {
                    seats: formSeats.value,
                    outdoors: formOutdoor.checked,
                };
                let tableID = firebase.database().ref().child('table').child(this.bizdata.id).push().key;
                tableData.id = tableID;
                tableData.businessID = this.bizdata.id;

                let updates = {};
                updates['/table/' + this.bizdata.id +'/'+ tableID+'/'] = tableData;
                updates['/businesses/' + this.bizdata.id + '/num_tables/'] = ++this.bizdata.num_tables;
            
                firebase.database().ref().update(updates);
                $('#tableModal').modal('hide');
            });
        });


        newBizTableNode.innerHTML += `
            <div class="table-responsive table-responsive-data2">
                <table class="biz-table table table-data2">
                    <thead class>
                        <tr>
                            <th>Table ID</th>
                            <th>Seats</th>
                            <th>Outdoor</th>
                        </tr>
                    </thead>
                    <tbody id="table-root-`+this.bizdata.id+`">
                    </tbody>
                </table>
            </div>`;
        this.displayTable();
        return newBusinessNode;
    }

    displayTable(){
        this.tableRoot = document.getElementById("table-root-"+this.bizdata.id);
        this.tableRoot.innerHTML = '';
        this.tables.forEach(table => {
            table.display(this.tableRoot);
        });
        if(!this.tableCount)
            this.tableCount = document.getElementById("biz-tables-"+this.bizdata.id)
        this.tableCount.innerHTML = "Tables: "+this.bizdata.num_tables;
    }
}

class Table {
    constructor(obj) {
        this.tableData = obj;
    }
    display(tableRoot) {
        var newTableNode = document.createElement("tr");
        newTableNode.className = "tr-shadow";

        var check = `<i class="zmdi zmdi-check"></i>`;
        var noncheck = "";
        newTableNode.innerHTML+=`
            <td class="table-id">`+this.tableData.id+`</td>
            <td class="table-count">`+this.tableData.seats+`</td>
            <td class="table-outdoor">`+(this.tableData.outdoors? check:noncheck)+ `</td>`
        tableRoot.appendChild(newTableNode);
    }
}