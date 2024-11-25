// Backend API endpoints
const API_BASE_URL = "http://localhost:3000/api"; 
const CLOCK_API = `${API_BASE_URL}/clock`;
const SET_TIME_API = `${API_BASE_URL}/set-alien-time`;
const CONVERT_TO_ALIEN_API = `${API_BASE_URL}/convert-to-alien-time`;
const CONVERT_TO_EARTH_API = `${API_BASE_URL}/convert-to-earth`;

// Fetch the current Alien Time and update the clock
async function fetchAlienTime() {
    try {
        const response = await fetch(CLOCK_API);
        const alienTime = await response.json();
        document.getElementById("alien-clock").innerText = `Alien Time: ${alienTime.date}, ${alienTime.time}`;
    } catch (error) {
        console.error("Error fetching Alien Time:", error);
        document.getElementById("alien-clock").innerText = "Error loading Alien Time.";
    }
}

// Set Alien Time using user input
/**
 
 * @returns {Object} Alien time object with year, month, day, hour, minute, second.
 */
function getAlienTimeInput() {
    const year = document.getElementById("year").value;
    const month = document.getElementById("month").value;
    const day = document.getElementById("day").value;
    const hour = document.getElementById("hour").value;
    const minute = document.getElementById("minute").value;
    const second = document.getElementById("second").value;

    // Validate inputs
    if (!year || !month || !day || !hour || !minute || !second) {
        alert("Please fill in all fields for Alien Date and Time.");
        return null;
    }

    return {
        year: parseInt(year),
        month: parseInt(month),
        day: parseInt(day),
        hour: parseInt(hour),
        minute: parseInt(minute),
        second: parseInt(second),
    };
}

/**
 * Helper function to send a POST request to the backend API.
 * @param {string} url - API endpoint URL.
 * @param {Object} data - Payload to send in the POST request.
 * @returns {Promise<Object>} - Response from the backend as a JSON object.
 */
async function sendPostRequest(url, data) {
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Error in POST request:", error);
        alert("An error occurred while processing the request.");
        return null;
    }
}

/**
 * Sets the alien time on the backend.
 */
async function setAlienTime() {
    const alienTime = getAlienTimeInput();
    if (!alienTime) return;

    const result = await sendPostRequest(SET_TIME_API, alienTime);
    if (result) {
        alert(`Alien Time Set Successfully: ${result.message}`);
    }
}

/**
 * Converts alien time to Earth time using the backend.
 */
async function convertToEarth() {
    const alienTime = getAlienTimeInput();
    if (!alienTime) return;

    const earthTime = await sendPostRequest(CONVERT_TO_EARTH_API, alienTime);
    if (earthTime) {
        alert(`Converted Earth Time: ${earthTime.date}, ${earthTime.time}`);
    }
}

async function convertToAlien() {
    // Get Earth time input from the datetime-local field
    const earthTimeInput = document.getElementById("earth-time").value;

    // Validate the input
    if (!earthTimeInput) {
        alert("Please provide a valid Earth Time.");
        return;
    }

    try {
        // Use the reusable helper function to send the POST request
        const alienTime = await sendPostRequest(CONVERT_TO_ALIEN_API, { earthTime: earthTimeInput });

        // If the conversion was successful, display the result
        if (alienTime) {
            document.getElementById("alien-time-result").innerText = 
                `Alien Time: ${alienTime.date}, ${alienTime.time}`;
        } else {
            document.getElementById("alien-time-result").innerText = 
                "Failed to convert Earth Time to Alien Time.";
        }
    } catch (error) {
        console.error("Error during Earth-to-Alien time conversion:", error);
        document.getElementById("alien-time-result").innerText = 
            "An error occurred during conversion.";
    }
}

// Update the Alien Clock every Alien Second (0.5 Earth seconds)
setInterval(fetchAlienTime, 500);
fetchAlienTime(); // Initial fetch
