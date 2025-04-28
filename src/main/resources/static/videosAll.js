const videosContainer = document.getElementById("videosContainer");
const errorVideosContainer = document.getElementById("videosError");

function fetchVideoDirectories() {
    videosContainer.innerHTML = '<div class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Загрузка...</span></div></div>';

    fetch(`/api/videos`, {
        headers: {
            'Accept': 'application/json'
        }
    })
        .then(response => {
            console.log('Response status:', response.status); // Отладка
            console.log('Response headers:', response.headers.get('Content-Type')); // Отладка
            if (!response.ok) {
                throw new Error(`Error with video directories: ${response.status}`);
            }
            return response.json();
        })
        .then(videoDirectories => {
            console.log('Fetched directories:', videoDirectories); // Отладка
            renderVideoDirectories(videoDirectories);
        })
        .catch(error => {
            console.error('Ошибка при получении данных:', error);
            errorVideosContainer.innerHTML = '<div class="alert alert-danger">Ошибка загрузки данных: ' + error.message + '</div>';
        });
}

function renderVideoDirectories(videoDirectories) {
    videosContainer.innerHTML = '';

    const directories = videoDirectories || [];

    if (directories.length === 0) {
        videosContainer.innerHTML = '<p class="text-muted">Видео не найдены.</p>';
        return;
    }

    directories.forEach(directory => {
        // VideoDirectoryDTO не содержит title и description, используем id или videoFrames
        const directoryCard = `
            <div class="col-sm-6 col-md-4 mb-3">
                <a href="/videos/${directory.id}" class="card-link">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Directory ${directory.id}</h5>
                            <p class="card-text">${
            directory.videoFrames && directory.videoFrames.length > 0
                ? directory.videoFrames[0].title || 'Без названия'
                : 'Видео отсутствуют'}</p>
                             <h5 class="card-title">Directory ${directory.description}</h5>
                        </div>
                    </div>
                </a>
            </div>
        `;
        videosContainer.insertAdjacentHTML('beforeend', directoryCard);
    });
}
fetchVideoDirectories();

