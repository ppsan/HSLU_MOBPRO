---
title: Mobile Programming
subtitle: Zusammenfassung MOBPRO (FS19)
author: Pascal Kiser
---

# Einleitung

Android ist sowohl ein Betriebssystem wie auch eine Softwareplattform für mobile Geräte mit einem Marktanteil von etwa 87 Prozent.
Android basiert auf dem Linux Kernel und wird von der von Google gegründeten *Open Handset Alliance* entwickelt.

Online Dokumentation von Google:

* [Startseite](https://developer.android.com/)
* [Getting Started](http://developer.android.com/training/)
* [Introduction to Adroid](http://developer.android.com/guide/)
* [Package Index (APIs)](http://developer.android.com/reference/packages.html)

## Grundlagen einer Android Applikation

### Komponenten

Android Applikationen bestehen aus lose gekoppelten Komponenten. Die Android Runtime verwaltet die einzelnen Komponenten einer Applikation.

- Komponenten müssen beim System registriert werden, teilweise mit Rechten (*Privileges*)
- Mit dem *Intent*-Mechanismus kann eine Komponente eine andere aufrufen
- Der Lebenszyklus einer Komponente wird vom System verwaltet

Es existieren vier verschiedene Typen von Android Komponenten:

 Name | Beschreibung 
--- |--- 
*Activity* | UI-Komponente, typischerweise ein Bildschirm (Screen, View)
*Service* | Komponente ohne UI, läuft typischerweise im Hintergrund
*Broadcast Receiver* | Reagiert auf app-interne oder systemweite Nachrichten
*Content Provider* | Ermöglicht Datenaustausch zwischen Applikationen

**Activities** (`android.app.Activity`) entsprechen jeweils einem Bildschirm und reagieren auf Benutzereingaben. Ein App besteht aus mehreren Views, die auf einem Stack liegen.

**Services** (`android.app.Activity`) laufen normalerweise für unbeschränkte Zeit im Hintergrund, es gibt teilweise aber Activities für bestimmte Servicees. Bsp `MusicPlayerActivity` für `MusicPlayerService`.

**Broadcast Receivers** (`android.content.BroadcastReeiver`) reagieren auf Broadcast-Nachrichten des Apps und des Systems.

**Content Providers** (`android.content.ContentProvider`) ermöglichen den Datenaustausch zwischen Applikationen und bieten ein Standard-API zum Suchen, Löschen, Aktualisieren und Einfügen von Daten.

### Activities und Intents

Komponenten (z.B. Activities) rufen über Intents ($\approx$ Nachrichten) andere Komponenten auf. Diese Kommunikation ist offen, d.h. der Sender weiss nicht, ob der Empfänger existiert. Die Parameter werden untypisiert als Strings übergeben und vom Empfänger geprüft, geparst und interpretiert.

Es gibt zwei Arten von Intents:

- *Explizite Intents*: Komponente wird direkt adressiert
- *Implizite Extents*: System wählt passende Komponente aus

#### Beispiel: Expliziter Intent

```java
// Sender Activity
public void onClickSendBtn(final View btn) {
    Intent intent = new Intent(this, Receiver.class); // <- Expliziter Empfänger
    intent.putExtra("msg", "Hello World");
    startActivity(intent);
}
```

```java
// Receiver Activity
public void onCreate(Bundle savedInstanceState) {
    // ...
    Intent intent = getIntent();
    String msg = intent.getExtras().getString("msg");
    displayMessage(msg);
}
```

Damit die Activities verwendet werden können, müssen sie ausserdem im Manifest deklariert sein:

```xml
...
<activity android:name=".Sender">
<activity android:name=".Receiver">
...
```

#### Beispiel: Expliziter Intent

```java
// Sender Activity
Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW); // <-- Aktion definert statt Empfänger
intent.setData(Uri.parse("http://www.hslu.ch"));
startActivity(intent);
```

> Folie 30

### Android Manifest

Alle Komponenten einer Applikation müssen dem System bekannt sein. In der Datei `AndroidManifest.xml` befinden sich Informationen zu den Komponenten mit *Privileges*, *Permissions* und Einschränkungen (*Intent-Filter*).

Die Datei beinhaltet grundsätzlich die statischen Eigenschaften einer Applikation wie Java-Package Name, Rechte und die Deklaration aller Komponenten.

Beispiel `AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.helloworld">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

### Lebenszyklus und Zustände

## Das Android Betriebssystem

## Entwicklungsumgebung
