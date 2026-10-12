"""
Prueba Funcional 1: Login en FideiNexus
Autor: Duban Alexander Salas Napán
Descripción: Verifica el flujo de autenticación de usuarios
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
import time

def test_login():
    """
    Test: Inicio de sesión de usuario en FideiNexus
    
    Pasos:
    1. Abrir navegador en página de login
    2. Ingresar credenciales válidas
    3. Click en botón "Iniciar Sesión"
    4. Verificar redirección al dashboard
    
    Resultado esperado: Login exitoso y redirección a /dashboard
    """
    
    # Configuración del navegador (headless para CI/CD)
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    chrome_options.add_argument('--no-sandbox')
    chrome_options.add_argument('--disable-dev-shm-usage')
    
    driver = webdriver.Chrome(options=chrome_options)
    
    try:
        print("🌐 Test 1: Login en FideiNexus")
        print("=" * 50)
        
        # PASO 1: Navegar a la página de login
        print("📍 Paso 1: Navegando a página de login...")
        driver.get("http://localhost:4200/login")
        time.sleep(2)
        
        # PASO 2: Ingresar credenciales
        print("🔑 Paso 2: Ingresando credenciales...")
        email_field = driver.find_element(By.ID, "email")
        password_field = driver.find_element(By.ID, "password")
        
        email_field.send_keys("admin@fideinexus.com")
        password_field.send_keys("Admin123!")
        time.sleep(1)
        
        # PASO 3: Click en botón de login
        print("👆 Paso 3: Haciendo click en 'Iniciar Sesión'...")
        login_button = driver.find_element(By.CSS_SELECTOR, "button[type='submit']")
        login_button.click()
        
        # PASO 4: Verificar redirección al dashboard
        print("✅ Paso 4: Verificando redirección al dashboard...")
        WebDriverWait(driver, 10).until(
            EC.url_contains("/dashboard")
        )
        
        current_url = driver.current_url
        assert "/dashboard" in current_url, f"Error: URL actual es {current_url}"
        
        print("=" * 50)
        print("✅ TEST PASSED: Login exitoso")
        print(f"📊 Resultado: Usuario redirigido a {current_url}")
        
        return True
        
    except Exception as e:
        print("=" * 50)
        print(f"❌ TEST FAILED: {str(e)}")
        driver.save_screenshot("test1-login-error.png")
        return False
        
    finally:
        driver.quit()

if __name__ == "__main__":
    result = test_login()
    exit(0 if result else 1)
