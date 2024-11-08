const style = document.createElement('style');
style.textContent = `
.swal2-popup {
            animation: slide-in 0.5s forwards;
        }

        @keyframes slide-in {
            0% {
                transform: translateX(100%);
                opacity: 0;
            }
            100% {
                transform: translateX(0%);
                opacity: 1;
            }
        }
  `;
document.head.appendChild(style);
if (window.location.href.toLowerCase().includes("show_verified")) {
	showNotification("Напоминание", "Здесь находятся УЖЕ утверженные вами события.", "warning");
} else {
	showNotification("Напоминание", "Здесь находятся события, ожидающие утверждения.", "warning");
}
if (window.location.href.toLowerCase().includes("?edited")) {
	showNotification("Изменено!", "Изменения в событие внесены успешно", "success");
}

async function verify(id) {
	console.log("Verifying ID: " + id);
	if (await sendData("/admin/verify?id=" + id)) {
		deleteByName(id);
		showNotification("Утверждено", "Событие сохранено.", "success");
	}
}
async function edit(id) {
	console.log("Editing ID: " + id);
	document.getElementById('id').value = id;
	const event = await fetchData("/admin/getevent?id=" + id);
	let dateParts = String(event.date).split('.');
	let formattedDate = `${dateParts[0]}-${dateParts[1]}-${dateParts[2]}`;
	document.getElementById("date").value = formattedDate;
	document.getElementById('title').value = event.title;
	document.getElementById('description').value = event.content;
	document.getElementById('signature').value = event.author;
	openModal();
}
async function deny(id) {
	console.log("Removing ID: " + id);
	if (await sendData("/admin/deny?id=" + id)) {
		deleteByName(id);
		showNotification("Удалено", "Событие удалено.", "info");
	}
}

const sendData = async (url) => {
	var csrfToken = document.querySelector('input[name="_csrf"]').value;
	try {
		const urlEncodedData = new URLSearchParams("").toString();
		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded',
				'X-CSRF-Token': csrfToken // Передаем CSRF токен
			},
			body: urlEncodedData
		});

		if (!response.ok) {
			showNotification("Ошибка HTTP " + response.status, "Некорректный ответ от сервера.", "error");
			return false;
		}

		const responseData = await response.json();
		console.log('Response:', responseData);
		return true;
	} catch (error) {
		console.error('Error:', error);
		showNotification("Ошибка!", error.toString(), "error");
		return false;
	}
};
const sendJSONData = async (url, data) => {
	var csrfToken = document.querySelector('input[name="_csrf"]').value;
	// Опции для fetch запроса
	const options = {
		method: 'POST',
		headers: {
			'X-CSRF-TOKEN': csrfToken,
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	};
	try {
		const response = await fetch(url, options);
		if (!response.ok) {
			showNotification("Ошибка HTTP " + response.status, "Некорректный ответ от сервера.", "error");
			return false;
		}
		const responseData = await response.json();
		console.log('Response:', responseData);
		return true;
	} catch (error) {
		console.error('Error:', error);
		showNotification("Ошибка!", error.toString(), "error");
		return false;
	}
};
function showNotification(title, text, icon) {
	Swal.fire({
		title: title,
		text: text,
		icon: icon,
		toast: true,
		position: 'top-end',
		showConfirmButton: false,
		timer: 5000,
		timerProgressBar: true,
		showClass: {
			popup: 'swal2-show'
		},
		hideClass: {
			popup: 'swal2-hide'
		}
	});
}
function deleteByName(id) {
	var divsToDelete = document.querySelectorAll('div[name="' + id + '"]');
	divsToDelete.forEach(function(div) {
		div.remove();
	});
}
function changeView() {
	if (window.location.href.toLowerCase().includes("show_verified")) {
		window.location.href = "/admin"
	} else {
		window.location.href = "/admin?show_verified"
	}
}
function openModal() {
	document.getElementById('overlay').style.display = 'flex';
}
function closeModal() {
	document.getElementById('overlay').style.display = 'none';
}
async function submitForm() {
	const id = document.getElementById('id').value;
	const date = document.getElementById('date').value;
	const title = document.getElementById('title').value;
	const description = document.getElementById('description').value;
	const signature = document.getElementById('signature').value;
	if (!date || !title || !signature || !id) {
		alert('Пожалуйста, заполните обязательные поля: Дата, Заголовок, Подпись.');
		return;
	}
	const data = {
		id: id,
		date: date,
		title: title,
		content: description,
		author: signature
	};
	console.log(data);
	if (await sendJSONData("/api/addevent", data)) {
		if (window.location.href.toLowerCase().includes("show_verified")) {
			deleteByName(data.id);
			showNotification("Готово. Необходимо действие!", "Не забудьте заново утвердить событие.", "success");
		} else {
			window.location.href = "/admin?edited";
		}
	}
	document.getElementById('id').value = "";
	document.getElementById("date").valueAsDate = new Date();
	document.getElementById('title').value = "";
	document.getElementById('description').value = "";
	document.getElementById('signature').value = "";
	closeModal();
}