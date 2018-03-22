package com.luntan.deppon.model.common;

/**
 * @author 380853    mingruishen
 * @create 2018/1/27
 * jeesns
 */

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public abstract class MEnum<T extends MEnum> implements Comparable, Serializable {
//    private static Logger logger = LoggerFactory.getLogger(MEnum.class);
    private static final long serialVersionUID = -345228295818805040L;
    private static final Map<String, MEnum> EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
    private static Map<Class<MEnum>, Entry> cEnumClasses = new WeakHashMap();
    private String name;
    private String cname;
    private Number value;
    private String desc;
    protected transient String iToString = null;

    public MEnum() {
    }

    public static MEnum create() {
        return create((String) null, (Number) null, (String) null, (String) null);
    }

    public static MEnum create(String name) {
        return create(name, (Number) null, (String) null, (String) null);
    }

    public static MEnum create(String name, Number value) {
        return create(name, value, (String) null, (String) null);
    }

    public static MEnum create(Number value, String cname) {
        return create((String) null, value, cname, (String) null);
    }

    public static MEnum create(String name, Number value, String cname) {
        return create(name, value, cname, (String) null);
    }

    public static MEnum create(String name, Number value, String cname, String desc) {
        MEnum menum = init(name);
        if (StringUtils.isNotEmpty(name)) {
            menum.name = name;
        }

        menum.cname = cname;
        menum.value = value;
        menum.desc = desc;
        return menum;
    }

    private static MEnum init(String name) {
        try {
            Class<MEnum> enumClass = ClassUtils.getClass(Thread.currentThread().getContextClassLoader(), getCallerClassName());
            if (enumClass == null) {
                throw new IllegalArgumentException("EnumClass must not be null");
            } else {
                Class var3 = MEnum.class;
                MEnum.Entry entry;
                synchronized (MEnum.class) {
                    entry = (MEnum.Entry) cEnumClasses.get(enumClass);
                    if (entry == null) {
                        entry = createEntry(enumClass);
                        Map myMap = new WeakHashMap();
                        myMap.putAll(cEnumClasses);
                        myMap.put(enumClass, entry);
                        cEnumClasses = myMap;
                    }
                }

                MEnum enumObject = (MEnum) enumClass.newInstance();
                if (StringUtils.isNotEmpty(name)) {
                    if (entry.map.containsKey(name)) {
                        throw new IllegalArgumentException("The Enum name must be unique, '" + name + "' has already been added");
                    }

                    enumObject.name = name;
                    entry.map.put(name, enumObject);
                }

                entry.list.add(enumObject);
                return enumObject;
            }
        } catch (Exception var7) {
//            logger.error(var7.getMessage());
            return null;
        }
    }

    private static String getCallerClassName() {
        StackTraceElement[] callers = (new Throwable()).getStackTrace();
        String enumClass = MEnum.class.getName();
        StackTraceElement[] arr$ = callers;
        int len$ = callers.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            StackTraceElement caller = arr$[i$];
            String className = caller.getClassName();
            String methodName = caller.getMethodName();
            if (!enumClass.equals(className) && "<clinit>".equals(methodName)) {
                return className;
            }
        }

        throw new IllegalArgumentException("");
    }

    protected Object readResolve() {
        MEnum.Entry entry = (MEnum.Entry) cEnumClasses.get(this.getEnumClass());
        return entry == null ? null : entry.map.get(this.name());
    }

    private Class getEnumClass() {
        Class enumClass = this.getClass();
        synchronized (enumClass) {
            return enumClass;
        }
    }

    private static <T> MEnum.Entry getEntry(Class<T> enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        } else if (!MEnum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException("The Class must be a subclass of Enum");
        } else {
            MEnum.Entry entry = (MEnum.Entry) cEnumClasses.get(enumClass);
            if (entry == null) {
                try {
                    Class.forName(enumClass.getName(), true, enumClass.getClassLoader());
                    entry = (MEnum.Entry) cEnumClasses.get(enumClass);
                } catch (Exception var3) {
                    ;
                }
            }

            return entry;
        }
    }

    private static MEnum.Entry createEntry(Class enumClass) {
        MEnum.Entry entry = new MEnum.Entry();

        for (Class cls = enumClass.getSuperclass(); cls != null && cls != MEnum.class; cls = cls.getSuperclass()) {
            MEnum.Entry loopEntry = (MEnum.Entry) cEnumClasses.get(cls);
            if (loopEntry != null) {
                entry.list.addAll(loopEntry.list);
                entry.map.putAll(loopEntry.map);
                break;
            }
        }

        return entry;
    }

    public static <T> MEnum getEnum(Class<T> enumClass, String name) {
        MEnum.Entry entry = getEntry(enumClass);
        return entry == null ? null : (MEnum) entry.map.get(name);
    }

    public static <T> MEnum getEnum(Class<T> enumClass, Number value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        } else {
            List<MEnum> list = getEnumList(enumClass);
            Iterator it = list.iterator();

            MEnum enumeration;
            do {
                if (!it.hasNext()) {
                    return null;
                }

                enumeration = (MEnum) it.next();
            } while (enumeration.value() != value.intValue());

            return enumeration;
        }
    }

    public static <T> MEnum getEnumByCnName(Class<T> enumClass, String cnName) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        } else {
            List<MEnum> list = getEnumList(enumClass);
            Iterator it = list.iterator();

            MEnum enumeration;
            do {
                if (!it.hasNext()) {
                    return null;
                }

                enumeration = (MEnum) it.next();
            } while (!enumeration.cname.equalsIgnoreCase(cnName));

            return enumeration;
        }
    }

    public static <T> Map<String, MEnum> getEnumMap(Class<T> enumClass) {
        MEnum.Entry entry = getEntry(enumClass);
        return entry == null ? EMPTY_MAP : entry.unmodifiableMap;
    }

    public static <T> List getEnumList(Class<T> enumClass) {
        MEnum.Entry entry = getEntry(enumClass);
        return entry == null ? Collections.EMPTY_LIST : entry.unmodifiableList;
    }

    protected static Iterator<MEnum> iterator(Class<MEnum> enumClass) {
        return getEnumList(enumClass).iterator();
    }

    @Override
    public final boolean equals(Object other) {
        return other == this ? true : (other == null ? false : (other.getClass() == this.getClass() ? this.name.equals(((MEnum) other).name) : other.getClass().getName().equals(this.getClass().getName())));
    }

    @Override
    public int compareTo(Object other) {
        if (other.equals(this)) {
            return 0;
        } else if (other.getClass() != this.getClass()) {
            throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
        } else {
            return this.name.compareTo(((MEnum) other).name);
        }
    }

    public boolean isIn(Class<T> enumClass, int value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        } else {
            List<MEnum> list = getEnumList(enumClass);
            Iterator it = list.iterator();

            MEnum enumeration;
            do {
                if (!it.hasNext()) {
                    return false;
                }

                enumeration = (MEnum) it.next();
            } while (enumeration.getValue().intValue() != value);

            return true;
        }
    }

    @Override
    public String toString() {
        if (this.iToString == null) {
            String shortName = ClassUtils.getShortClassName(this.getEnumClass());
            this.iToString = shortName + "[" + this.name() + "]";
        }

        return this.iToString;
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() ^ this.value.hashCode();
    }

    public final int value() {
        return this.value.intValue();
    }

    public final byte byteValue() {
        return this.value.byteValue();
    }

    public final short shortValue() {
        return this.value.shortValue();
    }

    public final long longValue() {
        return this.value.longValue();
    }

    public final String name() {
        if (this.name == null) {
            Class enumClass = this.getEnumClass();
            MEnum.Entry entry = (MEnum.Entry) cEnumClasses.get(this.getEnumClass());
            entry.populateNames(enumClass);
        }

        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public Number getValue() {
        return this.value;
    }

    public String getCname() {
        return this.cname;
    }

    public String getDesc() {
        return this.desc;
    }

    private static class Entry {
        final Map<String, MEnum> map = new HashMap();
        final Map<String, MEnum> unmodifiableMap;
        final List<MEnum> list;
        final List<MEnum> unmodifiableList;

        protected Entry() {
            this.unmodifiableMap = Collections.unmodifiableMap(this.map);
            this.list = new ArrayList(25);
            this.unmodifiableList = Collections.unmodifiableList(this.list);
        }

        private final void populateNames(Class enumClass) {
            synchronized (enumClass) {
                Field[] fields = enumClass.getFields();
                Field[] arr$ = fields;
                int len$ = fields.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    Field field = arr$[i$];
                    int modifier = field.getModifiers();
                    if (Modifier.isPublic(modifier) && Modifier.isFinal(modifier) && Modifier.isStatic(modifier)) {
                        try {
                            Object value = field.get((Object) null);
                            String fname = field.getName();
                            Iterator l$ = this.unmodifiableList.iterator();

                            while (l$.hasNext()) {
                                MEnum enumObject = (MEnum) l$.next();
                                if (value != null && value.equals(enumObject) && enumObject.name == null && !this.unmodifiableMap.containsKey(fname)) {
                                    enumObject.name = fname;
                                    this.map.put(fname, enumObject);
                                    break;
                                }
                            }
                        } catch (Exception var14) {
//                            MEnum.logger.error(var14.getMessage());
                        }
                    }
                }

            }
        }
    }
}

