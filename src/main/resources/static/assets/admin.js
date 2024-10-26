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

async function verify(id) {
	console.log("Verifying ID: " + id);
	if (sendData("/admin/verify?id=" + id)) {
		deleteByName(id);
		showNotification("Утверждено", "Событие сохранено.", "success");
	}
}
async function deny(id) {
	console.log("Removing ID: " + id);
	if (sendData("/admin/deny?id=" + id)) {
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

function showNotification(title, text, icon) {
	Swal.fire({
		title: title,
		text: text,
		icon: icon,
		toast: true,
		position: 'top-end',
		showConfirmButton: false,
		timer: 3000,
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