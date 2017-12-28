from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('recognize_photo', views.recognize_photo, name='recognize_photo'),
]
