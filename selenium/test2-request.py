"""
Prueba Funcional 2: Crear Solicitud Sacramental
Autor: Duban Alexander Salas Napán
Descripción: Verifica el flujo de creación de solicitud de bautismo
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import Select
import time

def test_create_request():
    """
    Test: Crear solicitud de sacramento (Bautismo)
    
    Pasos:
    1. Login como usuario administrador
    2. Navegar a sección "Solicitudes"
    3. Click en "Nueva Solicitud"
    4. Llenar formulario de solicitud
    5. Enviar formulario
    6. Verificar mensaje de éxito
    
    Resultado esperado: Solicitud creada exitosamente
    """
    
    # Configuración del navegador (headless para CI/CD)
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    chrome_options.add_argument('--no-sandbox')
    chrome_options.add_argument('--disable-dev-shm-usage')
    
    driver = webdriver.Chrome(options=chrome_options)
    
    try:
        print("Test 2: Crear Solicitud Sacramental")
        print("=" * 50)
        
        # PASO 1: Login
        print("Paso 1: Realizando login...")
        driver.get("http://localhost:4200/login")
        time.sleep(2)
        
        email_field = driver.find_element(By.ID, "email")
        password_field = driver.find_element(By.ID, "password")
        email_field.send_keys("admin@fideinexus.com")
        password_field.send_keys("Admin123!")
        
        login_button = driver.find_element(By.CSS_SELECTOR, "button[type='submit']")
        login_button.click()
        time.sleep(3)
        
        # PASO 2: Navegar a Solicitudes
        print("Paso 2: Navegando a sección Solicitudes...")
        driver.get("http://localhost:4200/solicitudes")
        time.sleep(2)
        
        # PASO 3: Click en "Nueva Solicitud"
        print("Paso 3: Haciendo click en 'Nueva Solicitud'...")
        new_request_btn = WebDriverWait(driver, 10).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, "button.btn-new-request"))
        )
        new_request_btn.click()
        time.sleep(2)
        
        # PASO 4: Llenar formulario
        print("Paso 4: Llenando formulario de solicitud...")
        
        # Seleccionar sacramento
        sacrament_select = Select(driver.find_element(By.ID, "sacrament"))
        sacrament_select.select_by_visible_text("Bautismo")
        time.sleep(1)
        
        # Ingresar datos del solicitante
        applicant_name = driver.find_element(By.ID, "applicantName")
        applicant_name.send_keys("Juan Carlos Pérez")
        
        applicant_email = driver.find_element(By.ID, "applicantEmail")
        applicant_email.send_keys("juan.perez@example.com")
        
        request_date = driver.find_element(By.ID, "requestDate")
        request_date.send_keys("15/12/2024")
        
        comments = driver.find_element(By.ID, "comments")
        comments.send_keys("Solicitud de bautismo para mi hijo")
        
        time.sleep(1)
        
        # PASO 5: Enviar formulario
        print("Paso 5: Enviando formulario...")
        submit_button = driver.find_element(By.CSS_SELECTOR, "button[type='submit']")
        submit_button.click()
        
        # PASO 6: Verificar mensaje de éxito
        print("Paso 6: Verificando mensaje de éxito...")
        success_message = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, ".alert-success"))
        )
        
        assert "exitosamente" in success_message.text.lower(), \
            f"Error: Mensaje inesperado - {success_message.text}"
        
        print("=" * 50)
        print("TEST PASSED: Solicitud creada exitosamente")
        print(f"Resultado: {success_message.text}")
        
        return True
        
    except Exception as e:
        print("=" * 50)
        print(f"TEST FAILED: {str(e)}")
        driver.save_screenshot("test2-request-error.png")
        return False
        
    finally:
        driver.quit()

if __name__ == "__main__":
    result = test_create_request()
    exit(0 if result else 1)
