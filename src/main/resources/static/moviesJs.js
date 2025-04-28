document.addEventListener("DOMContentLoaded", function () {
    const moviesContainer = document.getElementById("moviesContainer");
    if (moviesContainer) {
        // Состояние пагинации и сортировки
        let state = {
            currentPage: 0,
            totalPages: 1,
            sortBy: "title",
            sortDir: "asc",
            size: 10
        };

        function fetchMovies(sortBy = state.sortBy, sortDir = state.sortDir, page = state.currentPage) {
            // Показываем спиннер
            moviesContainer.innerHTML = '<div class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Загрузка...</span></div></div>';
            fetch(`/api/movies?sortBy=${sortBy}&sortDir=${sortDir}&page=${page}&size=${state.size}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error: status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    // Обновляем состояние
                    state.currentPage = data.page;
                    state.totalPages = data.totalPages;
                    state.sortBy = sortBy;
                    state.sortDir = sortDir;
                    renderMovies(data);
                })
                .catch(error => {
                    console.error('Ошибка при получении данных:', error);
                    moviesContainer.innerHTML = '<div class="alert alert-danger">Ошибка загрузки данных: ' + error.message + '</div>';
                });
        }

        function renderMovies(data) {
            moviesContainer.innerHTML = '';
            const movies = data.content || [];
            if (!movies || movies.length === 0) {
                moviesContainer.innerHTML = '<div class="alert alert-info">Фильмы не найдены.</div>';
            } else {
                movies.forEach(movie => {
                    const movieCard = `
                        <div class="col-sm-6 col-md-4 mb-3">
                            <a href="/movie/${movie.id}" class="card-link">
                                <div class="card h-100">
                                    <img src="${movie.poster || 'https://via.placeholder.com/300x450'}" class="card-img-top" alt="${movie.title}">
                                    <div class="card-body">
                                        <h5 class="card-title">${movie.title} <span class="badge bg-success">${movie.rating || 'N/A'}</span></h5>
                                        <p class="card-text"><strong>Год:</strong> ${movie.releaseYear || 'Не указан'}</p>
                                        <p class="card-text"><strong>Режиссёр:</strong> ${movie.director || 'Не указан'}</p>
                                        <p class="card-text"><strong>Продолжительность:</strong> ${movie.duration ? movie.duration + ' мин' : 'Не указана'}</p>
                                        <p class="card-text">${movie.description || 'Описание отсутствует'}</p>
                                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#trailerModal${movie.id}">Трейлер</button>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <div class="modal fade" id="trailerModal${movie.id}" tabindex="-1" aria-labelledby="trailerModalLabel${movie.id}" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="trailerModalLabel${movie.id}">${movie.title}</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="ratio ratio-16x9">
                                            <iframe src="${movie.trailerUrl || 'https://www.youtube.com/embed/dQw4w9WgXcQ'}" title="Трейлер ${movie.title}" allowfullscreen></iframe>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`;
                    moviesContainer.insertAdjacentHTML('beforeend', movieCard);
                });
            }

            // Обновляем пагинацию
            const pageInfo = document.getElementById("pageInfo");
            const prevPageItem = document.getElementById("prevPage").parentElement;
            const nextPageItem = document.getElementById("nextPage").parentElement;

            pageInfo.textContent = `Страница ${state.currentPage + 1} из ${state.totalPages}`;
            prevPageItem.classList.toggle("disabled", state.currentPage === 0);
            nextPageItem.classList.toggle("disabled", state.currentPage >= state.totalPages - 1 || state.totalPages === 0);
        }

        const sortSelect = document.getElementById("sortSelect");
        sortSelect.addEventListener("change", function () {
            const [sortBy, sortDir] = this.value.split("-");
            state.currentPage = 0; // Сбрасываем страницу при смене сортировки
            fetchMovies(sortBy, sortDir);
        });

        // Обработчики для кнопок пагинации
        const prevPage = document.getElementById("prevPage");
        const nextPage = document.getElementById("nextPage");

        prevPage.addEventListener("click", () => {
            if (state.currentPage > 0) {
                state.currentPage--;
                fetchMovies();
            }
        });

        nextPage.addEventListener("click", () => {
            if (state.currentPage < state.totalPages - 1) {
                state.currentPage++;
                fetchMovies();
            }
        });

        // Загружаем фильмы при загрузке страницы
        fetchMovies();
    }
});