document.addEventListener("DOMContentLoaded", function () {
    const movieContainer = document.getElementById("movieContainer");
    if (movieContainer) {
        const pathParts = window.location.pathname.split('/');
        const movieId = pathParts[pathParts.length - 1];
        let currentPage = 0;
        let totalPages = 1;

        async function fetchMovie() {
            try {
                const response = await fetch(`/api/movie/${movieId}`);
                if (!response.ok) {
                    throw new Error(`HTTP error: status: ${response.status}`);
                }
                const movie = await response.json();
                await fetchComments(movieId, currentPage);
                renderMovie(movie);
            } catch (error) {
                console.error('Ошибка при получении данных:', error);
                movieContainer.innerHTML = '<div class="alert alert-danger">Ошибка загрузки данных: ' + error.message + '</div>';
            }
        }

        async function fetchComments(movieId, page) {
            try {
                const response = await fetch(`/api/comments?movieId=${movieId}&page=${page}&size=5&sortBy=time,desc`);
                if (!response.ok) {
                    throw new Error(`HTTP error: status: ${response.status}`);
                }
                const data = await response.json();
                window.comments = data.content;
                totalPages = data.totalPages;
                currentPage = page;
                return data;
            } catch (error) {
                console.error('Ошибка при получении комментариев:', error);
                document.getElementById("commentError").textContent = `Ошибка загрузки комментариев: ${error.message}`;
                document.getElementById("commentError").classList.remove("d-none");
            }
        }

        function renderMovie(movie) {
            console.log('Movie data:', movie);
            if (!movie) {
                movieContainer.innerHTML = '<div class="alert alert-warning">Фильм не найден</div>';
                return;
            }

            const commentsContent = renderComments();
            const commentForm = renderCommentForm(movie);
            const genresSection = renderGenres(movie);
            const paginationContent = renderPagination();

            const movieContent = `
        <div class="row mt-4">
            <div class="col-md-4">
                <img src="${movie.poster || 'https://via.placeholder.com/300x450?text=' + encodeURIComponent(movie.title)}" 
                     class="img-fluid rounded shadow" alt="${movie.title}">
                ${movie.trailerUrl ? `
                    <div class="mt-3">
                        <button class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#trailerModal">
                            <i class="bi bi-play-fill"></i> Смотреть трейлер
                        </button>
                    </div>
                ` : ''}
            </div>
            <div class="col-md-8">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h1 class="mb-0">${movie.title}</h1>
                    ${movie.rating ? `
                        <span class="badge bg-success fs-5">${movie.rating}</span>
                    ` : ''}
                </div>
                <div class="mb-4">
                    <span class="badge bg-secondary me-2">${movie.releaseYear || 'Год не указан'}</span>
                    ${movie.duration ? `<span class="badge bg-secondary me-2">${movie.duration} мин</span>` : ''}
                </div>
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">О фильме</h5>
                        <div class="row">
                            <div class="col-md-4">
                                <p><strong>Режиссёр:</strong></p>
                                <p><strong>Год выхода:</strong></p>
                                <p><strong>Продолжительность:</strong></p>
                                ${genresSection}
                            </div>
                            <div class="col-md-8">
                                <p>${movie.director || 'Не указан'}</p>
                                <p>${movie.releaseYear || 'Не указан'}</p>
                                <p>${movie.duration ? movie.duration + ' мин' : 'Не указана'}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <video 
                        id="my-video" 
                        class="video-js" 
                        controls 
                        preload="auto" 
                        width="640" 
                        height="360"
                        data-setup='{"errorDisplay": true}'>
                        <source src="${movie.source}" type="video/mp4">
                    </video>
                </div>
                <div>
                    <h4>Описание</h4>
                    <p class="lead">${movie.description || 'Описание отсутствует'}</p>
                </div>
                <div class="mt-4">
                    <h4>Комментарии</h4>
                    ${commentForm}
                    ${commentsContent}
                    ${paginationContent}
                </div>
            </div>
        </div>
        ${movie.trailerUrl ? `
            <div class="modal fade" id="trailerModal" tabindex="-1" aria-labelledby="trailerModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="trailerModalLabel">Трейлер "${movie.title}"</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="ratio ratio-16x9">
                                <iframe src="${movie.trailerUrl}" title="Трейлер ${movie.title}" allowfullscreen></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        ` : ''}
    `;
            movieContainer.innerHTML = movieContent;

            const videoElement = document.getElementById('my-video');
            if (videoElement) {
                const player = videojs(videoElement);
                player.on('error', () => {
                    console.error('Ошибка видео:', player.error());
                });
            }

            const commentFormElement = document.getElementById('commentForm');
            if (commentFormElement) {
                commentFormElement.addEventListener('submit', async (event) => {
                    event.preventDefault();
                    await submitComment(movie);
                });
            }

            const prevButton = document.getElementById('prevPage');
            const nextButton = document.getElementById('nextPage');
            if (prevButton) {
                prevButton.addEventListener('click', async () => {
                    if (currentPage > 0) {
                        await fetchComments(movieId, currentPage - 1);
                        renderMovie(movie);
                    }
                });
            }
            if (nextButton) {
                nextButton.addEventListener('click', async () => {
                    if (currentPage < totalPages - 1) {
                        await fetchComments(movieId, currentPage + 1);
                        renderMovie(movie);
                    }
                });
            }

            // Привязываем обработчики для лайков и дизлайков
            bindReactionHandlers(movie);
        }

        function bindReactionHandlers(movie) {
            document.querySelectorAll('.like-btn').forEach(button => {
                button.addEventListener('click', async () => {
                    const commentId = button.dataset.commentId;
                    await submitReaction(commentId, 'LIKE');
                    await fetchComments(movieId, currentPage);
                    renderMovie(movie);
                });
            });
            document.querySelectorAll('.dislike-btn').forEach(button => {
                button.addEventListener('click', async () => {
                    const commentId = button.dataset.commentId;
                    await submitReaction(commentId, 'DISLIKE');
                    await fetchComments(movieId, currentPage);
                    renderMovie(movie);
                });
            });
        }

        function renderCommentForm(movie) {
            const userId = localStorage.getItem('userId') || 1;
            if (!userId) {
                return '<p class="text-muted">Войдите, чтобы оставить комментарий.</p>';
            }

            return `
                <div class="card mb-4">
                    <div class="card-body">
                        <form id="commentForm">
                            <div class="mb-3">
                                <label for="commentText" class="form-label">Ваш комментарий</label>
                                <textarea class="form-control" id="commentText" name="text" rows="4" required></textarea>
                            </div>
                            <input type="hidden" name="filmId" value="${movie.id}">
                            <input type="hidden" name="userId" value="${userId}">
                            <button type="submit" class="btn btn-primary">Отправить</button>
                        </form>
                    </div>
                </div>
            `;
        }

        function renderComments() {
            const comments = window.comments || [];
            console.log('Comments data:', comments);
            if (!comments || !Array.isArray(comments) || comments.length === 0) {
                console.warn('No comments available');
                return '<p class="text-muted">Комментариев пока нет.</p>';
            }
            return comments
                .map(comment => `
            <div class="card mb-4">
                <div class="card-body">
                    <h6 class="card-title">${comment.username || 'Аноним'}</h6>
                    <p class="card-text">${comment.text || 'Без текста'}</p>
                    <p class="card-text"><small class="text-muted">${comment.time || 'Без времени'}</small></p>
                    <div class="mt-2">
                        <button class="btn btn-sm btn-outline-success like-btn" data-comment-id="${comment.id}">
                            👍 ${comment.likes || 0}
                        </button>
                        <button class="btn btn-sm btn-outline-danger dislike-btn" data-comment-id="${comment.id}">
                            👎 ${comment.dislikes || 0}
                        </button>
                    </div>
                </div>
            </div>
        `)
                .join('');
        }

        function renderGenres(movie) {
            console.log('Genres data:', movie.genres);
            if (!movie.genres || !Array.isArray(movie.genres) || movie.genres.length === 0) {
                return '<p>Жанры не указаны</p>';
            }
            return '<p><strong>Жанры:</strong></p><p>' +
                movie.genres.map(genre => genre.name).join(', ') +
                '</p>';
        }

        function renderPagination() {
            return `
                <nav aria-label="Comment pagination">
                    <ul class="pagination justify-content-center mt-3">
                        <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                            <button class="page-link" id="prevPage">Предыдущая</button>
                        </li>
                        <li class="page-item ${currentPage >= totalPages - 1 ? 'disabled' : ''}">
                            <button class="page-link" id="nextPage">Следующая</button>
                        </li>
                    </ul>
                </nav>
            `;
        }

        // 2. Fix the movie.js submitComment function to properly send JWT
        async function submitComment(movie) {
            const form = document.getElementById("commentForm");
            const formData = new FormData(form);
            const commentData = {
                filmId: Number(formData.get("filmId")),
                text: formData.get("text")
            };
            const loading = document.getElementById("loading") || document.createElement("div");
            const button = form.querySelector("button");
            const commentResult = document.getElementById("commentResult") || document.createElement("div");
            const commentError = document.getElementById("commentError") || document.createElement("div");

            if (loading) loading.classList.remove("d-none");
            if (button) button.disabled = true;
            if (commentError) commentError.classList.add("d-none");

            try {
                const token = localStorage.getItem("token");
                if (!token) {
                    throw new Error("Authentication required. Please login first.");
                }

                const controller = new AbortController();
                const timeoutId = setTimeout(() => controller.abort(), 5000);

                const response = await fetch("/api/comments", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify(commentData),
                    signal: controller.signal
                });

                clearTimeout(timeoutId);

                if (response.status === 401) {
                    throw new Error("Authentication expired. Please login again.");
                } else if (response.status === 400) {
                    throw new Error("Comment text cannot be empty");
                } else if (response.status === 404) {
                    throw new Error("Movie not found");
                } else if (!response.ok) {
                    throw new Error(`HTTP error: ${response.status}`);
                }

                const data = await response.json();
                if (commentResult) {
                    commentResult.innerHTML = `<div class="alert alert-success">Комментарий добавлен (ID: ${data.id || data.commentId})</div>`;
                }
                form.reset();
                currentPage = 0;
                await fetchComments(movieId, currentPage);
                renderMovie(movie);
            } catch (error) {
                console.error("Ошибка при отправке комментария:", error);
                if (commentError) {
                    commentError.textContent = error.name === "AbortError"
                        ? "Request timed out"
                        : error.message.includes("Failed to fetch")
                            ? "Network error"
                            : `Ошибка: ${error.message}`;
                    commentError.classList.remove("d-none");
                }

                if (error.message.includes("Authentication")) {
                    setTimeout(() => {
                        window.location.href = "/authfront/login.html";
                    }, 2000);
                }
            } finally {
                if (loading) loading.classList.add("d-none");
                if (button) button.disabled = false;
            }
        }

        async function submitReaction(commentId, type) {
            const loading = document.getElementById("loading");
            const commentError = document.getElementById("commentError");

            loading.classList.remove("d-none");
            commentError.classList.add("d-none");

            try {
                const token = localStorage.getItem("token");
                const controller = new AbortController();
                const timeoutId = setTimeout(() => controller.abort(), 5000);

                const response = await fetch(`/api/comments/${commentId}/reactions`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify({ type }),
                    signal: controller.signal
                });

                clearTimeout(timeoutId);

                if (!response.ok) {
                    throw new Error(`HTTP error: ${response.status}`);
                }
            } catch (error) {
                console.error("Ошибка при отправке реакции:", error);
                commentError.textContent = error.name === "AbortError"
                    ? "Request timed out"
                    : error.message.includes("Failed to fetch")
                        ? "Network error"
                        : `Ошибка: ${error.message}`;
                commentError.classList.remove("d-none");
            } finally {
                loading.classList.add("d-none");
            }
        }

        fetchMovie();
    }
});
