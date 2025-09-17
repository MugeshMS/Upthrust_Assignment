# Frontend Setup & Usage

This section explains how to run and use the frontend of the **Mini Workflow Automation** project.

---

## **1️⃣ Open Frontend in Browser**

### Option A: Standalone HTML/JS
1. Navigate to the `src/main/resources/static` folder (if Spring Boot) or wherever your `index.html` resides.  
2. Open `index.html` in your browser by **double-clicking** it.  
3. You should see:
   - Input field for prompt
   - Dropdown to select API (Weather, GitHub, News, All)
   - Submit button
   - Result display area

> ✅ Limitation: If running standalone, AJAX calls to backend may be blocked by CORS. Recommended to run backend locally.

---

### Option B: Through Spring Boot (Recommended)
1. Make sure your backend is running on `http://localhost:8080/`  
   ```bash
   ./mvnw spring-boot:run
