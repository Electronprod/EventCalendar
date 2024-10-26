async function main() {
	moment.locale('ru');
	let verstate = window.location.href.toLowerCase().includes("/admin");
	if (window.location.href.toLowerCase().includes("show_verified")) {
		verstate = false;
	}
	const eventsData = await fetchData("/api/getevents?verified=" + !verstate);

	function renderEvents(events) {
		const container = document.getElementById("events-container");
		let upcomingEventFound = false;
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
                  <i class="fa fa-calendar"></i> ${date.format('dddd')} <br>
                  <i class="fa fa-signature"></i> ${event.author}
              </div>
              <div class="event-content">
                  ${event.content}
              </div>
          </div>
          <div class="button-container" name="${event.id}">
          	<button class="verify-btn" onclick="verify(${event.id})">Утвердить</button>
          	<button class="deny-btn" onclick="deny(${event.id})">Удалить</button>
          </div>
        `;
			// Если событие еще не прошло, выделим его
			if (date.isSameOrAfter(moment()) && !upcomingEventFound) {
				eventElement.classList.add("upcoming-event");
				upcomingEventFound = eventElement; // Сохраняем ссылку на предстоящее событие
			}
			container.appendChild(eventElement);
		});
		if (window.location.href.toLowerCase().includes("show_verified")) {
			const buttons = document.querySelectorAll('button.verify-btn');
			buttons.forEach(button => {
				button.remove();
			});
		}
		// Прокручиваем к предстоящему событию
		if (upcomingEventFound) {
			upcomingEventFound.scrollIntoView({ behavior: "smooth", block: "start" });
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
