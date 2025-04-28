document.addEventListener("DOMContentLoaded", function () {
    const filmSearchContainer = document.getElementById("containerSearch");
    const searchForm = document.getElementById("movieSearchForm");

    if (filmSearchContainer && searchForm) {
        searchForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // не перезагружаем страницу

            const title = document.getElementById("searchName").value;
            const year = document.getElementById("searchYear").value;
            const genre = document.getElementById("searchGenre").value;

            const searchData = {
                title: title.trim(),
                year: year ? Number(year) : undefined,
                genre: genre.trim()
            };

            try {
                const response = await fetch('/api/movies/search', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(searchData)
                });

                if (!response.ok) {
                    throw new Error(`Ошибка HTTP: ${response.status}`);
                }

                const result = await response.json();
                renderSearchResults(result); // функция вывода фильмов

            } catch (error) {
                console.error('Ошибка при поиске фильмов:', error);
                filmSearchContainer.innerHTML = `<div class="alert alert-danger">Ошибка загрузки: ${error.message}</div>`;
            }
        });

        function renderSearchResults(movies) {
            if (!movies || movies.length === 0) {
                filmSearchContainer.innerHTML = '<p class="text-muted">Ничего не найдено.</p>';
                return;
            }
            filmSearchContainer.innerHTML = movies.map(movie => `
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <img src="${movie.poster || 'https://via.placeholder.com/300x450?text=No+Image'}" class="card-img-top" alt="${movie.title}">
                        <div class="card-body">
                            <h5 class="card-title">${movie.title}</h5>
                            <p class="card-text">${movie.description || 'Без описания'}</p>
                            <p class="card-text"><small class="text-muted">${movie.releaseYear || 'Год не указан'}</small></p>
                        </div>
                    </div>
                </div>
            `).join('');
        }
    }
});
