async function main() {
	moment.locale('ru');
	let verstate = window.location.href.toLowerCase().includes("/admin");
	if (window.location.href.toLowerCase().includes("show_verified")) {
		verstate = false;
	}
	const eventsData = await fetchData("/api/getevents?verified=" + !verstate);
	eventsData.sort((a, b) => {
		const dateA = moment(a.date, "YYYY.MM.DD");
		const dateB = moment(b.date, "YYYY.MM.DD");
		const monthDifference = dateA.month() - dateB.month();
		if (monthDifference !== 0) {
			return monthDifference;
		}
		return dateA.date() - dateB.date();
	});

	function renderEvents(events) {
		const container = document.getElementById("events-container");
		events.forEach(event => {
			const date = moment(event.date, "YYYY.MM.DD");
			const eventElement = document.createElement("div");
			eventElement.classList.add("event");

			eventElement.innerHTML = `
          <div class="event-date" name="${event.id}">
              <div class="day">${date.format('D')}</div>
              <div class="month">${date.format('MMMM')}</div>
          </div>
          <div class="event-details" name="${event.id}">
              <h3 class="event-title">${event.title}</h3>
              <div class="event-meta">
                  <i class="fa fa-signature"></i> ${event.author}
              </div>
              <div class="event-content">
                  ${event.content}
              </div>
          </div>
          <div class="button-container" name="${event.id}">
            <button class="verify-btn" onclick="verify(${event.id})">Утвердить</button>
            <button class="edit-btn" onclick="edit(${event.id})">Редактировать</button>
            <button class="deny-btn" onclick="deny(${event.id})">Удалить</button>
          </div>
        `;
			container.appendChild(eventElement);
		});
		if (window.location.href.toLowerCase().includes("show_verified")) {
			const buttons = document.querySelectorAll('button.verify-btn');
			buttons.forEach(button => {
				button.remove();
			});
		}
	}
	renderEvents(eventsData);
}

window.onload = main;

async function fetchData(url) {
	try {
		const response = await fetch(url);
		if (!response.ok) {
			throw new Error(`Ошибка HTTP: ${response.status}`);
		}
		return await response.json();
	} catch (error) {
		console.error('Ошибка при получении данных:', error);
		return null;
	}
}
