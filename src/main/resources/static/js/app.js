const API_BASE = '/employees';

// 1. Fetch All Employees (GET)
async function fetchEmployees() {
    try {
        const response = await fetch(`${API_BASE}/displayAll`);
        const data = await response.json();
        renderTable(data);
    } catch (error) {
        showMessage("Failed to connect to the server. Is Spring Boot running?", "error");
    }
}

// Render data into the HTML table
function renderTable(employees) {
    const tbody = document.getElementById('employeeTableBody');
    tbody.innerHTML = ''; // Clear current rows

    employees.forEach(emp => {
        const row = `<tr>
            <td>${emp.employeeId}</td>
            <td>${emp.employeeName}</td>
            <td>${emp.employeeEmail}</td>
            <td>${emp.location}</td>
            <td class="actions">
                <button onclick="fetchOneEmployee('${emp.employeeId}')">Edit</button>
                <button class="danger" onclick="deleteEmployee('${emp.employeeId}')">Delete</button>
            </td>
        </tr>`;
        tbody.innerHTML += row;
    });
}

// 2. Fetch One Employee for Editing (GET)
async function fetchOneEmployee(id) {
    try {
        const response = await fetch(`${API_BASE}/display/${id}`);
        if(response.ok) {
            const emp = await response.json();

            document.getElementById('employeeId').value = emp.employeeId;
            document.getElementById('employeeId').readOnly = true;
            document.getElementById('employeeName').value = emp.employeeName;
            document.getElementById('employeeEmail').value = emp.employeeEmail;
            document.getElementById('location').value = emp.location;

            document.getElementById('isUpdate').value = "true";
            document.getElementById('formTitle').innerText = "Update Employee";
            document.getElementById('submitBtn').innerText = "Update";
        }
    } catch (error) {
        showMessage("Error fetching employee details.", "error");
    }
}

// 3. Save or Update Employee (POST / PUT)
async function handleFormSubmit(event) {
    event.preventDefault();

    const isUpdate = document.getElementById('isUpdate').value === "true";
    const id = document.getElementById('employeeId').value;

    const payload = {
        employeeId: id,
        employeeName: document.getElementById('employeeName').value,
        employeeEmail: document.getElementById('employeeEmail').value,
        location: document.getElementById('location').value
    };

    const url = isUpdate ? `${API_BASE}/update/${id}` : API_BASE;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            showMessage(isUpdate ? "Employee Updated!" : "Employee Saved!", "success");
            resetForm();
            fetchEmployees();
        } else {
            showMessage("Error saving data.", "error");
        }
    } catch (error) {
        showMessage("Network error.", "error");
    }
}

// 4. Delete Employee (DELETE)
async function deleteEmployee(id) {
    if (!confirm(`Are you sure you want to delete employee ${id}?`)) return;

    try {
        const response = await fetch(`${API_BASE}/delete/${id}`, { method: 'DELETE' });
        if (response.ok) {
            showMessage("Employee deleted.", "success");
            fetchEmployees();
        }
    } catch (error) {
        showMessage("Error deleting employee.", "error");
    }
}

// 5. Bulk Insert (POST)
async function handleBulkInsert() {
    const jsonText = document.getElementById('bulkJson').value;
    try {
        const payload = JSON.parse(jsonText);

        const response = await fetch(`${API_BASE}/bulk`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            showMessage("Bulk insert successful!", "success");
            document.getElementById('bulkJson').value = '';
            fetchEmployees();
        } else {
            showMessage("Bulk insert failed on the server.", "error");
        }
    } catch (error) {
        showMessage("Invalid JSON format. Please check your syntax.", "error");
    }
}

// Utility: Reset Form
function resetForm() {
    document.getElementById('employeeForm').reset();
    document.getElementById('employeeId').readOnly = false;
    document.getElementById('isUpdate').value = "false";
    document.getElementById('formTitle').innerText = "Add New Employee";
    document.getElementById('submitBtn').innerText = "Save Employee";
}

// Utility: Show temporary message
function showMessage(text, type) {
    const box = document.getElementById('messageBox');
    box.innerText = text;
    box.className = type;
    box.style.display = 'block';
    setTimeout(() => { box.style.display = 'none'; }, 3000);
}

// Load data on startup
window.onload = fetchEmployees;