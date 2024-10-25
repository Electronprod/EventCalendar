document.addEventListener("DOMContentLoaded", function() {
	moment.locale('ru'); // Устанавливаем локализацию на русский

	const eventsData = [
		{
			date: "2024.10.23",
			title: "Ice Cream Social",
			content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
			author: "12:30 PM - 2:00 PM"
		},
		{
			date: "2024.10.27",
			title: "Operations Meeting",
			content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
			author: "2:30 PM - 4:00 PM"
		}
	];

	function renderEvents(events) {
		const container = document.getElementById("events-container");
		let upcomingEventFound = false;
		events.forEach(event => {
			const date = moment(event.date, "YYYY.MM.DD");
			const eventElement = document.createElement("div");
			eventElement.classList.add("event");

			eventElement.innerHTML = `
          <div class="event-date">
              <div class="day">${date.format('D')}</div>
              <div class="month">${date.format('MMMM')}</div>
          </div>
          <div class="event-details">
              <h3 class="event-title">${event.title}</h3>
              <div class="event-meta">
                  <i class="fa fa-calendar"></i> ${date.format('dddd')} <br>
                  <i class="fa fa-signature"></i> ${event.author}
              </div>
              <div class="event-content">
                  ${event.content}
              </div>
          </div>
        `;
			// Если событие еще не прошло, выделим его
			if (date.isSameOrAfter(moment()) && !upcomingEventFound) {
				eventElement.classList.add("upcoming-event");
				upcomingEventFound = eventElement; // Сохраняем ссылку на предстоящее событие
			}
			container.appendChild(eventElement);
		});
		// Прокручиваем к предстоящему событию
		if (upcomingEventFound) {
			upcomingEventFound.scrollIntoView({ behavior: "smooth", block: "start" });
		}
	}
	renderEvents(eventsData);
});
