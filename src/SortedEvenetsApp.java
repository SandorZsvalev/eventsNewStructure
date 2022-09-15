import java.util.*;

public class SortedEvenetsApp {
    public static void main(String[] args) {

        Event eventOne = new Event("127.0.0.1", "21.04.2022", "google.com", Action.REQUEST, ActionState.OK);
        Event eventTwo = new Event("127.4.0.3", "22.04.2022", "yahoo.com", Action.DOWNLOAD, ActionState.FAIL);
        Event eventThree = new Event("127.2.0.1", "23.04.2022", "amazon.com", Action.RESPONSE, ActionState.OK);
        Event eventFour = new Event("127.4.0.2", "24.04.2022", "shopping.com", Action.UPLOAD, ActionState.FAIL);

        List<Event> eventList = new ArrayList<>();
        eventList.add(eventOne);
        eventList.add(eventTwo);
        eventList.add(eventThree);
        eventList.add(eventFour);
/*
2. Написать компараторы для трех полей, такие что бы каждый отличался друг от друга логикой сравнения, придумать свою.
3. Написать метод, принимающий:
- массив объектов (arraylist events),
- компаратор
возвращающий:
отсортированную структуру данных TreeMap (<Events>,
в которой объекты из (arraylist events) будут являться ключом,
а значением будут являться список со всеми остальными объектами (arraylist events), кроме этого.

отсортированными в порядке обратном переданному компаратору.
 */

        Comparator<Event> compareByDestination = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getDestination().compareTo(o2.getDestination());
            }
        };

        Comparator<Event> compareByAction = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getAction().compareTo(o2.getAction());
            }
        };

        Comparator<Event> compareBySource = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                String[] sourceDataOne = o1.getSource().split("\\.");
                String[] sourceDataTwo = o2.getSource().split("\\.");
                for (int i = 0; i <= 3; i++) {
                    int dif = Integer.parseInt(sourceDataOne[i]) - Integer.parseInt(sourceDataTwo[i]);
                    if (dif != 0) {
                        return dif;
                    }
                }
                return 0;
            }
        };

        printSortedEventsMap(eventsNewStructure(eventList, compareBySource));
    }


    private static void printSortedEventsMap(Map<Event, List<Event>> allEventMap) {
        for (Map.Entry<Event, List<Event>> pair : allEventMap.entrySet()) {
            Event event = pair.getKey();
            List<Event> eventThreeSortedList = pair.getValue();
            System.out.println("Событие" + event);
            System.out.println("Лист событий" + eventThreeSortedList);
        }

    }

    private static Map<Event, List<Event>> eventsNewStructure(List<Event> events, Comparator<Event> comparator) {
        events.sort(comparator);
        Map<Event, List<Event>> allEventMap = new TreeMap<>(comparator);
        for (Event event : events) {
            List<Event> reversedThreeEventList = new ArrayList<>();
            Iterator<Event> eventsIterator = events.iterator();
            while (eventsIterator.hasNext()) {
                Event nextEvent = eventsIterator.next();
                if (!nextEvent.equals(event)) {
                    reversedThreeEventList.add(nextEvent);
                }
            }
            reversedThreeEventList.sort(comparator.reversed());
            allEventMap.put(event, reversedThreeEventList);
        }
        return allEventMap;
    }

}
